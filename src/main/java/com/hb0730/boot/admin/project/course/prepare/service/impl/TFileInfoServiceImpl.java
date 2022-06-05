package com.hb0730.boot.admin.project.course.prepare.service.impl;

import com.hb0730.boot.admin.commons.utils.FebsUtil;
import com.hb0730.boot.admin.commons.utils.JdDateTimeUtil;
import com.hb0730.boot.admin.cos.CosService;
import com.hb0730.boot.admin.domain.result.PageVO;
import com.hb0730.boot.admin.project.course.prepare.dto.CourseFileInfoParams;
import com.hb0730.boot.admin.project.course.prepare.dto.TFileInfoDTO;
import com.hb0730.boot.admin.project.course.prepare.entity.TFileInfoDO;
import com.hb0730.boot.admin.project.course.prepare.service.TFileInfoService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: \
 * @author: qiaojinfeng3
 * @date: 2022/5/3 22:44
 */
@Service
@RequiredArgsConstructor
public class TFileInfoServiceImpl implements TFileInfoService {
    private final CosService cosService;
    private final MongoTemplate mongoTemplate;

    @Override
    public PageVO<TFileInfoDTO> findPageListWrapper(CourseFileInfoParams params) {
        PageVO<TFileInfoDO> pageList = findPageList(params);
        return transform(pageList);
    }

    @Override
    public PageVO<TFileInfoDO> findPageList(CourseFileInfoParams orchestrateParams) {
        final Query query = getQuery(orchestrateParams);
        final long count = mongoTemplate.count(query, TFileInfoDO.class);
        // page
        final int pageNum = orchestrateParams.getPageNum().intValue();
        final int pageSize = orchestrateParams.getPageSize().intValue();
        query.with(PageRequest.of(pageNum - 1, pageSize))
            .with(Sort.by(Sort.Direction.DESC, "createTime"));
        final List<TFileInfoDO> list = mongoTemplate.find(query, TFileInfoDO.class);
        return new PageVO<TFileInfoDO>().setRecords(list).setTotal(count);
    }

    private PageVO<TFileInfoDTO> transform(PageVO<TFileInfoDO> pageVO) {
        if (null == pageVO) {
            return new PageVO<>();
        }

        /**/
        List<TFileInfoDO> records = pageVO.getRecords();
        Map<String, TFileInfoDTO> infoDTOMap = records.stream().collect(HashMap::new, (map, record) -> {
                String id = record.getId();
                String fileName = record.getFileName();
                String fileType = record.getFileType();
                String ossKey = record.getOssKey();
                Long createTime = record.getCreateTime();
                boolean image = FebsUtil.isImage(fileType);
                boolean audio = FebsUtil.isAudio(fileType);
                boolean video = FebsUtil.isVideo(fileType);
                String date = JdDateTimeUtil.formatDate(createTime);
                if (image) {
                    String key = date + "所有图片";
                    TFileInfoDTO imageTag = getImageTag(id, key, fileName, cosService.getPublicUrl(ossKey));
                    if (map.containsKey(key)) {
                        TFileInfoDTO oldValue = map.get(key);
                        oldValue.setContent(oldValue.getContent() + imageTag.getContent());
                        map.put(key, oldValue);
                    } else {
                        map.put(key, imageTag);
                    }
                } else if (audio) {
                    String key = date + "所有音频";
                    TFileInfoDTO audioTag = getAudioTag(id, key, fileName, cosService.getPublicUrl(ossKey));
                    if (map.containsKey(key)) {
                        TFileInfoDTO oldValue = map.get(key);
                        oldValue.setContent(oldValue.getContent() + audioTag.getContent());
                        map.put(key, oldValue);
                    } else {
                        map.put(key, audioTag);
                    }
                } else if (video) {
                    String key = date + "所有视频";
                    TFileInfoDTO videoTag = getVideoTag(id, key, fileName, cosService.getPublicUrl(ossKey));
                    if (map.containsKey(key)) {
                        TFileInfoDTO oldValue = map.get(key);
                        oldValue.setContent(oldValue.getContent() + videoTag.getContent());
                        map.put(key, oldValue);
                    } else {
                        map.put(key, videoTag);
                    }
                }
            },
            HashMap::putAll
        );
        List<TFileInfoDTO> list = new ArrayList<>(infoDTOMap.values());
        Collections.sort(list);
        return new PageVO<TFileInfoDTO>()
            .setRecords(list)
            .setTotal(list.size());
    }

    private TFileInfoDTO getImageTag(String id, String key, String title, String src) {
        String content = new StringBuilder("<p><strong>")
            .append("<img src='").append(src).append("'")
            .append(" title='").append(title).append("'")
            .append("/>")
            .append("</strong></p>")
            .toString();
        return new TFileInfoDTO(id, key, "包含" + key, content);
    }

    private TFileInfoDTO getAudioTag(String id, String key, String title, String src) {
        String content = new StringBuilder("<p><b>").append(title).append("</b></p>")
            .append("<p>")
            .append("<audio src='").append(src).append("'")
            .append(" controls='controls'")
            .append("/>")
            .append("</p>")
            .toString();
        return new TFileInfoDTO(id, key, "包含" + key, content);
    }

    private TFileInfoDTO getVideoTag(String id, String key, String title, String src) {
        String content = new StringBuilder("<p><strong>")
            .append("<video src='").append(src).append("'")
            .append(" title='").append(title).append("' ")
            .append("/>")
            .append("</strong></p>")
            .toString();
        return new TFileInfoDTO(id, key, "包含" + key, content);
    }

    private Query getQuery(CourseFileInfoParams orchestrateParams) {
        if (null == orchestrateParams) {
            return null;
        }

        /* query */
        String id = orchestrateParams.getId();
        Integer latestDays = orchestrateParams.getLatestDays();
        final List<String> sortColumns = orchestrateParams.getSortColumn();
        final String sortType = orchestrateParams.getSortType();
        Query query = new Query();
        if (StringUtils.isNotBlank(id)) {
            query.addCriteria(Criteria.where("_id").is(id));
        }
        if (null != latestDays) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime startTime = now.minusDays(latestDays).with(LocalTime.MIN);
            LocalDateTime endTime = now.with(LocalTime.MAX);
            query.addCriteria(
                Criteria.where("createTime")
                    .gte(JdDateTimeUtil.getMillisecond(startTime))
                    .lte(JdDateTimeUtil.getMillisecond(endTime))
            );
        }
        if (null != sortColumns && !sortColumns.isEmpty()) {
            if (Sort.Direction.ASC.name().equalsIgnoreCase(sortType)) {
                query.with(Sort.by(Sort.Direction.ASC, sortColumns.toArray(new String[]{})));
            } else {
                query.with(Sort.by(Sort.Direction.DESC, sortColumns.toArray(new String[]{})));
            }
        }
        return query;
    }
}
