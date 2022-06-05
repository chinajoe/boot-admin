package com.hb0730.boot.admin.project.course.prepare.service;

import com.hb0730.boot.admin.domain.result.PageVO;
import com.hb0730.boot.admin.project.course.prepare.dto.CourseFileInfoParams;
import com.hb0730.boot.admin.project.course.prepare.dto.TFileInfoDTO;
import com.hb0730.boot.admin.project.course.prepare.entity.TFileInfoDO;

/**
 * The interface T file info service.
 *
 * @description:
 * @author: qiaojinfeng3
 * @date: 2022 /5/3 22:44
 */
public interface TFileInfoService {
    /**
     * Find page list wrapper page vo.
     *
     * @param params the params
     * @return the page vo
     */
    PageVO<TFileInfoDTO> findPageListWrapper(CourseFileInfoParams params);

    /**
     * Find page list page vo.
     *
     * @param orchestrateParams the orchestrate params
     * @return the page vo
     */
    PageVO<TFileInfoDO> findPageList(CourseFileInfoParams orchestrateParams);
}
