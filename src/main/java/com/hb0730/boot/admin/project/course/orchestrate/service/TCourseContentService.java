package com.hb0730.boot.admin.project.course.orchestrate.service;

import com.hb0730.boot.admin.project.course.orchestrate.dto.CourseContentAddDTO;
import com.hb0730.boot.admin.project.course.orchestrate.dto.CourseContentParams;
import com.hb0730.boot.admin.project.course.orchestrate.dto.CourseContentUpdateDTO;
import com.hb0730.boot.admin.project.course.orchestrate.entity.TCourseContentDO;

/**
 * The interface T course content service.
 *
 * @description:
 * @author: qiaojinfeng3
 * @date: 2022 /5/3 22:44
 */
public interface TCourseContentService {
    /**
     * Add t course content do.
     *
     * @param addDTO the add dto
     * @return the t course content do
     */
    TCourseContentDO add(CourseContentAddDTO addDTO);

    /**
     * Update by id boolean.
     *
     * @param updateDTO the update dto
     * @return the boolean
     */
    boolean updateById(CourseContentUpdateDTO updateDTO);

    /**
     * Detail t course content do.
     *
     * @param courseContentParams the course content params
     * @return the t course content do
     */
    TCourseContentDO detail(CourseContentParams courseContentParams);
}
