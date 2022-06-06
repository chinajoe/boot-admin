package com.hb0730.boot.admin.project.course.prepare.service;

import com.hb0730.boot.admin.project.course.orchestrate.dto.CourseContentAddDTO;

import java.io.IOException;

/**
 * The interface T prepare service.
 *
 * @description:
 * @author: qiaojinfeng3
 * @date: 2022 -06-06
 */
public interface TPrepareService {
    /**
     * Content 2 image boolean.
     *
     * @param params the params
     * @return the boolean
     */
    boolean content2Image(CourseContentAddDTO params) throws IOException;

}
