package com.hb0730.boot.admin.project.course.orchestrate.service;

import com.hb0730.boot.admin.domain.result.PageVO;
import com.hb0730.boot.admin.project.course.orchestrate.dto.OrchestrateAddDTO;
import com.hb0730.boot.admin.project.course.orchestrate.dto.OrchestrateParams;
import com.hb0730.boot.admin.project.course.orchestrate.entity.TCourseDO;

/**
 * The interface T course service.
 *
 * @description:
 * @author: qiaojinfeng3
 * @date: 2022 /5/3 22:44
 */
public interface TCourseService {
    /**
     * Find  list.
     *
     * @param orchestrateParams orchestrate params
     * @return the list
     */
    PageVO<TCourseDO> findPageList(OrchestrateParams orchestrateParams);

    /**
     * Add boolean.
     *
     * @param addDTO the add dto
     * @return the boolean
     */
    TCourseDO add(OrchestrateAddDTO addDTO);
}
