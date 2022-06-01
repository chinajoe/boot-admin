package com.hb0730.boot.admin.project.course.orchestrate.controller;

import com.alibaba.fastjson.JSON;
import com.hb0730.boot.admin.domain.result.R;
import com.hb0730.boot.admin.domain.result.Result;
import com.hb0730.boot.admin.project.course.orchestrate.convert.TCourseContentConvert;
import com.hb0730.boot.admin.project.course.orchestrate.dto.CourseContentAddDTO;
import com.hb0730.boot.admin.project.course.orchestrate.dto.CourseContentParams;
import com.hb0730.boot.admin.project.course.orchestrate.dto.CourseContentUpdateDTO;
import com.hb0730.boot.admin.project.course.orchestrate.dto.TCourseContentDTO;
import com.hb0730.boot.admin.project.course.orchestrate.entity.TCourseContentDO;
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
     * Detail result.
     *
     * @param courseContentParams the course content params
     * @return the result
     */
    @RequestMapping("/detail")
    public Result<TCourseContentDTO> detail(CourseContentParams courseContentParams) {
        log.info("detail--param:{}", courseContentParams);
        TCourseContentDO tCourseContentDO = tCourseContentService.detail(courseContentParams);
        TCourseContentDTO tCourseContentDTO = TCourseContentConvert.INSTANCE.toDto(tCourseContentDO);
        log.info("detail--result:{}", tCourseContentDTO);
        return R.success(tCourseContentDTO);
    }

    /**
     * Save result.
     *
     * @param addDTO the add dto
     * @return the result
     */
    @PostMapping("/save")
    public Result<String> save(@RequestBody CourseContentAddDTO addDTO) {
        log.info("add--param:{}", JSON.toJSONString(addDTO));
        tCourseContentService.add(addDTO);
        return R.success(null);
    }

    /**
     * Update result.
     *
     * @param updateDTO the update dto
     * @return the result
     */
    @RequestMapping("/update/{id}")
    public Result<Boolean> update(@RequestBody CourseContentUpdateDTO updateDTO) {
        log.info("update--param:{}", updateDTO);
        boolean updateById = tCourseContentService.updateById(updateDTO);
        log.info("update--result:{}", updateById);
        return R.success(updateById);
    }
}
