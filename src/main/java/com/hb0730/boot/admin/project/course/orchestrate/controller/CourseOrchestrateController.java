package com.hb0730.boot.admin.project.course.orchestrate.controller;

import com.hb0730.boot.admin.domain.result.PageVO;
import com.hb0730.boot.admin.domain.result.R;
import com.hb0730.boot.admin.domain.result.Result;
import com.hb0730.boot.admin.project.course.orchestrate.convert.TCourseConvert;
import com.hb0730.boot.admin.project.course.orchestrate.dto.OrchestrateAddDTO;
import com.hb0730.boot.admin.project.course.orchestrate.dto.OrchestrateParams;
import com.hb0730.boot.admin.project.course.orchestrate.dto.OrchestrateUpdateDTO;
import com.hb0730.boot.admin.project.course.orchestrate.dto.TCourseDTO;
import com.hb0730.boot.admin.project.course.orchestrate.entity.TCourseDO;
import com.hb0730.boot.admin.project.course.orchestrate.service.TCourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    @RequestMapping("/list/page")
    public Result<PageVO<TCourseDTO>> list(@RequestBody OrchestrateParams params) {
        log.info("list--param:{}", params);
        final PageVO<TCourseDO> pageVO = tCourseService.findPageList(params);
        log.info("list--pageVO:{}", pageVO);
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
    @RequestMapping("/save")
    public Result<Boolean> add(@RequestBody OrchestrateAddDTO addDTO) {
        log.info("add--param:{}", addDTO);
        tCourseService.add(addDTO);
        return R.success(null);
    }

    /**
     * Update result.
     *
     * @param updateDTO the update dto
     * @return the result
     */
    @RequestMapping("/update/{id}")
    public Result<Boolean> update(@RequestBody OrchestrateUpdateDTO updateDTO) {
        log.info("update--param:{}", updateDTO);
        tCourseService.updateById(updateDTO);
        return R.success(null);
    }

    /**
     * Delete result.
     *
     * @param ids the ids
     * @return the result
     */
    @RequestMapping("/delete")
    public Result<Boolean> delete(@RequestBody List<String> ids) {
        log.info("delete--param ids:{}", ids);
        tCourseService.deleteByIds(ids);
        return R.success(null);
    }
}
