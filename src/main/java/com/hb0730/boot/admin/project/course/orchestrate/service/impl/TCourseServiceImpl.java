package com.hb0730.boot.admin.project.course.orchestrate.service.impl;

import com.hb0730.boot.admin.domain.result.PageVO;
import com.hb0730.boot.admin.infrastructure.TCourseRepository;
import com.hb0730.boot.admin.project.course.orchestrate.convert.TCourseConvert;
import com.hb0730.boot.admin.project.course.orchestrate.dto.OrchestrateAddDTO;
import com.hb0730.boot.admin.project.course.orchestrate.dto.OrchestrateParams;
import com.hb0730.boot.admin.project.course.orchestrate.entity.TCourseDO;
import com.hb0730.boot.admin.project.course.orchestrate.service.TCourseService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

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
        final int pageNum = orchestrateParams.getPageNum().intValue();
        final int pageSize = orchestrateParams.getPageSize().intValue();
        final Query query = getQuery(orchestrateParams)
            .with(PageRequest.of(pageNum - 1, pageSize));
        final long count = mongoTemplate.count(query, TCourseDO.class);
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

    private Query getQuery(OrchestrateParams orchestrateParams) {
        if (null == orchestrateParams) {
            return null;
        }

        /* query */
        final List<String> sortColumns = orchestrateParams.getSortColumn();
        final String sortType = orchestrateParams.getSortType();
        final String courseName = orchestrateParams.getCourseName();
        Query query = new Query();
        if (StringUtils.isNotBlank(courseName)) {
            query.addCriteria(Criteria.where("courseName").regex(".*" + courseName + ".*"));
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
