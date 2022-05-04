package com.hb0730.boot.admin.project.course.orchestrate.controller;

import com.alibaba.fastjson.JSON;
import com.hb0730.boot.admin.domain.result.PageVO;
import com.hb0730.boot.admin.domain.result.R;
import com.hb0730.boot.admin.domain.result.Result;
import com.hb0730.boot.admin.project.course.orchestrate.convert.TCourseConvert;
import com.hb0730.boot.admin.project.course.orchestrate.dto.OrchestrateAddDTO;
import com.hb0730.boot.admin.project.course.orchestrate.dto.OrchestrateParams;
import com.hb0730.boot.admin.project.course.orchestrate.dto.TCourseDTO;
import com.hb0730.boot.admin.project.course.orchestrate.entity.TCourseDO;
import com.hb0730.boot.admin.project.course.orchestrate.service.TCourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The type Course orchestrate controller.
 *
 * @description: 课程编排
 * @author: qiaojinfeng3
 * @date: 2022 /4/20 20:01
 */
@Slf4j
@RestController
@RequestMapping("/course/orchestrate")
@RequiredArgsConstructor
public class CourseOrchestrateController {
    private final TCourseService tCourseService;

    /**
     * List result.
     *
     * @param params the params
     * @return the result
     */
    @GetMapping("/list")
    public Result<PageVO<TCourseDTO>> list(OrchestrateParams params) {
        log.info("list--param:{}", JSON.toJSONString(params));
        final PageVO<TCourseDO> pageVO = tCourseService.findPageList(params);
        log.info("list--pageVO:{}", JSON.toJSONString(pageVO));
        final List<TCourseDTO> tCourseDTOS = TCourseConvert.INSTANCE.toDto(pageVO.getRecords());
        // page
        final PageVO<TCourseDTO> dtoPageVO = new PageVO<TCourseDTO>()
                .setRecords(tCourseDTOS)
                .setTotal(pageVO.getTotal());
        return R.success(dtoPageVO);
    }

    /**
     * Add result.
     *
     * @param addDTO the add dto
     * @return the result
     */
    @PostMapping("/add")
    public Result add(@RequestBody OrchestrateAddDTO addDTO) {
        log.info("add--param:{}", JSON.toJSONString(addDTO));
        tCourseService.add(addDTO);
        return R.success(null);
    }
}
