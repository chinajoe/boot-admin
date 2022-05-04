package com.hb0730.boot.admin.project.course.orchestrate.service;

import com.hb0730.boot.admin.project.course.orchestrate.dto.CourseContentAddDTO;
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
}
