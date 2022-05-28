package com.hb0730.boot.admin.project.course.orchestrate.service.impl;

import com.hb0730.boot.admin.domain.result.PageVO;
import com.hb0730.boot.admin.infrastructure.TCourseRepository;
import com.hb0730.boot.admin.project.course.orchestrate.convert.TCourseConvert;
import com.hb0730.boot.admin.project.course.orchestrate.dto.OrchestrateAddDTO;
import com.hb0730.boot.admin.project.course.orchestrate.dto.OrchestrateParams;
import com.hb0730.boot.admin.project.course.orchestrate.dto.OrchestrateUpdateDTO;
import com.hb0730.boot.admin.project.course.orchestrate.entity.TCourseDO;
import com.hb0730.boot.admin.project.course.orchestrate.service.TCourseService;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.UpdateDefinition;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @description:
 * @author: qiaojinfeng3
 * @date: 2022/5/4 21:38
 */
@Service
@RequiredArgsConstructor
public class TCourseServiceImpl implements TCourseService {
    private final TCourseRepository tCourseRepository;
    private final MongoTemplate mongoTemplate;

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

    private UpdateDefinition getUpdate(OrchestrateUpdateDTO updateDTO) {
        assert null != updateDTO;
        // get
        String courseName = updateDTO.getCourseName();
        String courseCover = updateDTO.getCourseCover();
        String fkCourseAudioId = updateDTO.getFkCourseAudioId();
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
