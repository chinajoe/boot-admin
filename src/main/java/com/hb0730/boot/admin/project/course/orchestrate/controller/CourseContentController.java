package com.hb0730.boot.admin.project.course.orchestrate.controller;

import com.alibaba.fastjson.JSON;
import com.hb0730.boot.admin.domain.result.R;
import com.hb0730.boot.admin.domain.result.Result;
import com.hb0730.boot.admin.project.course.orchestrate.dto.CourseContentAddDTO;
import com.hb0730.boot.admin.project.course.orchestrate.service.TCourseContentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Course orchestrate controller.
 *
 * @description: 课程内容
 * @author: qiaojinfeng3
 * @date: 2022 /4/20 20:01
 */
@Slf4j
@RestController
@RequestMapping("/course/content")
@RequiredArgsConstructor
public class CourseContentController {
    private final TCourseContentService tCourseContentService;

    /**
     * Save result.
     *
     * @param addDTO the add dto
     * @return the result
     */
    @PostMapping("/save")
    public Result save(@RequestBody CourseContentAddDTO addDTO) {
        log.info("add--param:{}", JSON.toJSONString(addDTO));
        tCourseContentService.add(addDTO);
        return R.success(null);
    }
}
