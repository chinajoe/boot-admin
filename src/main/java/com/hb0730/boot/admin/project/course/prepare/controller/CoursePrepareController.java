package com.hb0730.boot.admin.project.course.prepare.controller;

import com.hb0730.boot.admin.domain.result.R;
import com.hb0730.boot.admin.domain.result.Result;
import com.hb0730.boot.admin.project.course.orchestrate.dto.CourseContentAddDTO;
import com.hb0730.boot.admin.project.course.prepare.service.TPrepareService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * The type Course prepare controller.
 *
 * @description: 课程准备
 * @author: qiaojinfeng3
 * @date: 2022 /4/20 20:02
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/course/prepare")
public class CoursePrepareController {
    private final TPrepareService tPrepareService;

    /**
     * Export result.
     *
     * @param params the params
     * @return the result
     */
    @RequestMapping("/export")
    public Result<String> export(@RequestBody CourseContentAddDTO params) throws IOException {
        log.info("export--param:{}", params);
        boolean content2Image = tPrepareService.content2Image(params);
        log.info("export--content2Image:{}", content2Image);
        return R.success();
    }
}
