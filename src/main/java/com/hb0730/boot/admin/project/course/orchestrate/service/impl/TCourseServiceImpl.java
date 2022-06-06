package com.hb0730.boot.admin.project.course.orchestrate.service.impl;

import com.alibaba.fastjson2.JSON;
import com.hb0730.boot.admin.commons.utils.FutureUtil;
import com.hb0730.boot.admin.cos.CosService;
import com.hb0730.boot.admin.domain.model.dto.KV;
import com.hb0730.boot.admin.domain.model.dto.UploadFileInfo;
import com.hb0730.boot.admin.domain.result.PageVO;
import com.hb0730.boot.admin.infrastructure.TCourseRepository;
import com.hb0730.boot.admin.infrastructure.TFileInfoRepository;
import com.hb0730.boot.admin.project.course.orchestrate.convert.TCourseConvert;
import com.hb0730.boot.admin.project.course.orchestrate.dto.OrchestrateAddDTO;
import com.hb0730.boot.admin.project.course.orchestrate.dto.OrchestrateParams;
import com.hb0730.boot.admin.project.course.orchestrate.dto.OrchestrateUpdateDTO;
import com.hb0730.boot.admin.project.course.orchestrate.dto.TCourseDTO;
import com.hb0730.boot.admin.project.course.orchestrate.entity.TCourseDO;
import com.hb0730.boot.admin.project.course.orchestrate.service.TCourseService;
import com.hb0730.boot.admin.project.course.prepare.entity.TFileInfoDO;
import com.hb0730.commons.lang.io.FileUtils;
import com.mongodb.client.result.UpdateResult;
import com.qcloud.cos.transfer.Upload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.UpdateDefinition;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: qiaojinfeng3
 * @date: 2022/5/4 21:38
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TCourseServiceImpl implements TCourseService {
    private final TCourseRepository tCourseRepository;
    private final TFileInfoRepository tFileInfoRepository;
    private final MongoTemplate mongoTemplate;
    private final CosService cosService;
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    public void setThreadPoolTaskExecutor(@Qualifier(value = "threadPoolTaskExecutor") ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

    @Override
    public PageVO<TCourseDO> findPageList(OrchestrateParams orchestrateParams) {
        final Query query = getQuery(orchestrateParams);
        final long count = mongoTemplate.count(query, TCourseDO.class);
        // page
        final int pageNum = orchestrateParams.getPageNum().intValue();
        final int pageSize = orchestrateParams.getPageSize().intValue();
        query.with(PageRequest.of(pageNum - 1, pageSize))
            .with(Sort.by(Sort.Direction.DESC, "createTime"));
        final List<TCourseDO> list = mongoTemplate.find(query, TCourseDO.class);
        return new PageVO<TCourseDO>().setRecords(list).setTotal(count);
    }

    @Override
    public PageVO<TCourseDTO> findPageListWrapper(OrchestrateParams orchestrateParams) {
        PageVO<TCourseDO> pageVO = findPageList(orchestrateParams);
        List<TCourseDTO> tCourseDTOS = transform(pageVO);
        // page
        return new PageVO<TCourseDTO>()
            .setRecords(tCourseDTOS)
            .setTotal(pageVO.getTotal());
    }

    private List<TCourseDTO> transform(PageVO<TCourseDO> pageVO) {
        List<TCourseDO> records = pageVO.getRecords();
        Set<String> courseCoverIdSet = records.stream()
            .map(tCourseDO -> {
                String courseCover = tCourseDO.getCourseCover();
                String fkCourseAudioId = tCourseDO.getFkCourseAudioId();
                return Arrays.asList(courseCover, fkCourseAudioId);
            })
            .flatMap(Collection::stream)
            .collect(Collectors.toSet());
        Iterable<TFileInfoDO> tFileInfoDOS = tFileInfoRepository.findAllById(courseCoverIdSet);
        Map<String, KV<String, String>> idAndResignedUrlMap = new HashMap<>();
        for (TFileInfoDO tFileInfoDO : tFileInfoDOS) {
            String id = tFileInfoDO.getId();
            String fileName = tFileInfoDO.getFileName();
            String ossKey = tFileInfoDO.getOssKey();
            String resignedUrl = cosService.generateResignedUrl(ossKey);
            idAndResignedUrlMap.put(id, new KV<>(fileName, resignedUrl));
        }
        return TCourseConvert.INSTANCE.toDto(records, idAndResignedUrlMap);
    }

    @Override
    public TCourseDO add(OrchestrateAddDTO addDTO) {
        if (null == addDTO) {
            return null;
        }
        final TCourseDO tCourseDO = TCourseConvert.INSTANCE.toDo(addDTO);
        return tCourseRepository.save(tCourseDO);
    }

    @Override
    public boolean updateById(OrchestrateUpdateDTO updateDTO) {
        String id = updateDTO.getId();
        Query query = getQuery(new OrchestrateParams(id));
        UpdateDefinition update = getUpdate(updateDTO);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, TCourseDO.class);
        return updateResult.getMatchedCount() > 0;
    }

    @Override
    public boolean deleteByIds(Collection<String> ids) {
        if (null == ids || ids.isEmpty()) {
            return false;
        }
        tCourseRepository.deleteAllById(ids);
        return true;
    }

    @Override
    public Collection<UploadFileInfo> upload(Collection<File> files) {
        if (null == files || files.isEmpty()) {
            return Collections.emptyList();
        }

        /* deal: save file infos */
        Collection<TFileInfoDO> fileInfoDOCollection = tFileInfoRepository.saveAll(files);
        return doUpload(files, fileInfoDOCollection);
    }

    @Override
    public Collection<UploadFileInfo> upload(String pathPrefix, Collection<File> files) {
        if (null == files || files.isEmpty()) {
            return Collections.emptyList();
        }

        /* deal: save file infos */
        Collection<TFileInfoDO> fileInfoDOCollection = tFileInfoRepository.saveAll(pathPrefix, files);
        return doUpload(files, fileInfoDOCollection);
    }

    private Collection<UploadFileInfo> doUpload(Collection<File> files, Collection<TFileInfoDO> fileInfoDOCollection) {
        Map<String, String> fileNameAndOssKeyMap = new HashMap<>();
        fileInfoDOCollection.forEach(tFileInfoDO -> {
            String fileName = tFileInfoDO.getFileName();
            String ossKey = tFileInfoDO.getOssKey();
            fileNameAndOssKeyMap.put(fileName, ossKey);
        });
        // upload async to cos
        for (File file : files) {
            String name = file.getName();
            String ossKey = fileNameAndOssKeyMap.get(name);
            FutureUtil.runAsync(() -> {
                long currentTimeMillis = System.currentTimeMillis();
                Upload upload = cosService.upload(ossKey, file.getAbsolutePath());
                try {
                    upload.waitForCompletion();
                    log.info("upload file:{} cost:{}", ossKey, (System.currentTimeMillis() - currentTimeMillis));
                } catch (InterruptedException e) {
                    log.error("TCourseService--upload--file:{}", JSON.toJSONString(file), e);
                } finally {
                    boolean deleteFile = FileUtils.deleteFile(file);
                    log.info("delete local file:{} result:{}", file.getAbsolutePath(), deleteFile);
                }
            }, threadPoolTaskExecutor);
        }
        return fileInfoDOCollection.stream()
            .map(tFileInfoDO -> {
                String id = tFileInfoDO.getId();
                String fileName = tFileInfoDO.getFileName();
                String ossKey = fileNameAndOssKeyMap.get(fileName);
                String publicUrl = cosService.getPublicUrl(ossKey);
                return new UploadFileInfo(id, fileName, publicUrl);
            })
            .collect(Collectors.toList());
    }

    private UpdateDefinition getUpdate(OrchestrateUpdateDTO updateDTO) {
        assert null != updateDTO;
        // get
        String courseName = updateDTO.getCourseName();
        String courseCover = updateDTO.getCourseCover().getId();
        String fkCourseAudioId = updateDTO.getCourseAudio().getId();
        String courseDescription = updateDTO.getCourseDescription();
        // set
        Update update = new Update();
        update.set("updateTime", System.currentTimeMillis());
        if (StringUtils.isNotBlank(courseName)) {
            update.set("courseName", courseName);
        }
        if (StringUtils.isNotBlank(courseCover)) {
            update.set("courseCover", courseCover);
        }
        if (StringUtils.isNotBlank(fkCourseAudioId)) {
            update.set("fkCourseAudioId", fkCourseAudioId);
        }
        if (StringUtils.isNotBlank(courseDescription)) {
            update.set("courseDescription", courseDescription);
        }
        return update;
    }

    private Query getQuery(OrchestrateParams orchestrateParams) {
        if (null == orchestrateParams) {
            return null;
        }

        /* query */
        String id = orchestrateParams.getId();
        final List<String> sortColumns = orchestrateParams.getSortColumn();
        final String sortType = orchestrateParams.getSortType();
        final String courseName = orchestrateParams.getCourseName();
        Integer publishStatus = orchestrateParams.getPublishStatus();
        Query query = new Query();
        if (StringUtils.isNotBlank(id)) {
            query.addCriteria(Criteria.where("_id").is(id));
        }
        if (StringUtils.isNotBlank(courseName)) {
            query.addCriteria(Criteria.where("courseName").regex(courseName));
        }
        if (null != sortColumns && !sortColumns.isEmpty()) {
            if (Sort.Direction.ASC.name().equalsIgnoreCase(sortType)) {
                query.with(Sort.by(Sort.Direction.ASC, sortColumns.toArray(new String[]{})));
            } else {
                query.with(Sort.by(Sort.Direction.DESC, sortColumns.toArray(new String[]{})));
            }
        }
        if (null != publishStatus) {
            query.addCriteria(Criteria.where("publishStatus").is(publishStatus));
        }
        return query;
    }
}
