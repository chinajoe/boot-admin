package com.hb0730.boot.admin.project.course.orchestrate.service;

import com.hb0730.boot.admin.domain.result.PageVO;
import com.hb0730.boot.admin.project.course.orchestrate.dto.OrchestrateAddDTO;
import com.hb0730.boot.admin.project.course.orchestrate.dto.OrchestrateParams;
import com.hb0730.boot.admin.project.course.orchestrate.dto.OrchestrateUpdateDTO;
import com.hb0730.boot.admin.project.course.orchestrate.dto.TCourseDTO;
import com.hb0730.boot.admin.project.course.orchestrate.entity.TCourseDO;

import java.io.File;
import java.util.Collection;

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
     * Find page list wrapper page vo.
     *
     * @param orchestrateParams params
     * @return the page vo
     */
    PageVO<TCourseDTO> findPageListWrapper(OrchestrateParams orchestrateParams);

    /**
     * Add boolean.
     *
     * @param addDTO the add dto
     * @return the boolean
     */
    TCourseDO add(OrchestrateAddDTO addDTO);

    /**
     * Update by id boolean.
     *
     * @param updateDTO the update dto
     * @return the boolean
     */
    boolean updateById(OrchestrateUpdateDTO updateDTO);

    /**
     * Delete by ids boolean.
     *
     * @param ids the ids
     * @return the boolean
     */
    boolean deleteByIds(Collection<String> ids);

    /**
     * Upload collection.
     *
     * @param files the files
     * @return the collection
     */
    Collection<String> upload(Collection<File> files);
}
