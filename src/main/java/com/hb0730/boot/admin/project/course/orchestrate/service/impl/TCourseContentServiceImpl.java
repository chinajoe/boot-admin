package com.hb0730.boot.admin.project.course.orchestrate.service.impl;

import com.hb0730.boot.admin.infrastructure.TCourseContentRepository;
import com.hb0730.boot.admin.project.course.orchestrate.convert.TCourseContentConvert;
import com.hb0730.boot.admin.project.course.orchestrate.dto.CourseContentAddDTO;
import com.hb0730.boot.admin.project.course.orchestrate.dto.CourseContentParams;
import com.hb0730.boot.admin.project.course.orchestrate.dto.CourseContentUpdateDTO;
import com.hb0730.boot.admin.project.course.orchestrate.entity.TCourseContentDO;
import com.hb0730.boot.admin.project.course.orchestrate.service.TCourseContentService;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.UpdateDefinition;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: qiaojinfeng3
 * @date: 2022/5/4 22:58
 */
@Service
@RequiredArgsConstructor
public class TCourseContentServiceImpl implements TCourseContentService {
    private final TCourseContentRepository tCourseContentRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public TCourseContentDO add(CourseContentAddDTO addDTO) {
        if (null == addDTO) {
            return null;
        }
        final TCourseContentDO tCourseContentDO = TCourseContentConvert.INSTANCE.toDo(addDTO);
        return tCourseContentRepository.save(tCourseContentDO);
    }

    @Override
    public boolean updateById(CourseContentUpdateDTO updateDTO) {
        String id = updateDTO.getId();
        Query query = getQuery(new CourseContentParams(id));
        UpdateDefinition update = getUpdate(updateDTO);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, TCourseContentDO.class);
        return updateResult.getMatchedCount() > 0;
    }

    @Override
    public TCourseContentDO detail(CourseContentParams courseContentParams) {
        Query query = getQuery(courseContentParams);
        return mongoTemplate.findOne(query, TCourseContentDO.class);
    }

    private UpdateDefinition getUpdate(CourseContentUpdateDTO updateDTO) {
        assert null != updateDTO;
        // get
        String content = updateDTO.getContent();
        String htmlContent = updateDTO.getHtmlContent();
        Integer publishType = updateDTO.getPublishType();
        String fkCourseId = updateDTO.getFkCourseId();
        // set
        Update update = new Update();
        update.set("updateTime", System.currentTimeMillis());
        if (StringUtils.isNotBlank(content)) {
            update.set("content", content);
        }
        if (StringUtils.isNotBlank(htmlContent)) {
            update.set("htmlContent", htmlContent);
        }
        if (null != publishType) {
            update.set("publishType", publishType);
        }
        if (StringUtils.isNotBlank(fkCourseId)) {
            update.set("fkCourseId", fkCourseId);
        }
        return update;
    }

    private Query getQuery(CourseContentParams params) {
        if (null == params) {
            return null;
        }

        /* query */
        String id = params.getId();
        final List<String> sortColumns = params.getSortColumn();
        String fkCourseId = params.getFkCourseId();
        final String sortType = params.getSortType();
        Query query = new Query();
        if (StringUtils.isNotBlank(id)) {
            query.addCriteria(Criteria.where("_id").is(id));
        }
        if (StringUtils.isNotBlank(fkCourseId)) {
            query.addCriteria(Criteria.where("fkCourseId").is(fkCourseId));
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
