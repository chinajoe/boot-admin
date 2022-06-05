package com.hb0730.boot.admin.project.course.prepare.controller;

import com.hb0730.boot.admin.domain.result.PageVO;
import com.hb0730.boot.admin.domain.result.R;
import com.hb0730.boot.admin.domain.result.Result;
import com.hb0730.boot.admin.project.course.prepare.dto.CourseFileInfoParams;
import com.hb0730.boot.admin.project.course.prepare.dto.TFileInfoDTO;
import com.hb0730.boot.admin.project.course.prepare.service.TFileInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 课程准备
 * @author: qiaojinfeng3
 * @date: 2022/4/20 20:02
 */
@Slf4j
@RestController
@RequestMapping("/course/file")
@RequiredArgsConstructor
public class CourseFileInfoController {
    private final TFileInfoService tFileInfoService;

    /**
     * List result.
     *
     * @param params the params
     * @return the result
     */
    @RequestMapping("/list/page")
    public Result<PageVO<TFileInfoDTO>> list(@RequestBody CourseFileInfoParams params) {
        log.info("list--param:{}", params);
        final PageVO<TFileInfoDTO> pageVO = tFileInfoService.findPageListWrapper(params);
        log.info("list--pageVO:{}", pageVO);
        return R.success(pageVO);
    }
}
