package com.hb0730.boot.admin.project.course.orchestrate.controller;

import com.hb0730.boot.admin.domain.result.PageVO;
import com.hb0730.boot.admin.domain.result.R;
import com.hb0730.boot.admin.domain.result.Result;
import com.hb0730.boot.admin.project.course.orchestrate.dto.OrchestrateAddDTO;
import com.hb0730.boot.admin.project.course.orchestrate.dto.OrchestrateParams;
import com.hb0730.boot.admin.project.course.orchestrate.dto.OrchestrateUpdateDTO;
import com.hb0730.boot.admin.project.course.orchestrate.dto.TCourseDTO;
import com.hb0730.boot.admin.project.course.orchestrate.service.TCourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.hb0730.boot.admin.commons.enums.ResponseStatusEnum.FILE_OVERSIZE;
import static com.hb0730.boot.admin.commons.enums.ResponseStatusEnum.PARAMS_REQUIRED_IS_NULL;

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
        final PageVO<TCourseDTO> pageVO = tCourseService.findPageListWrapper(params);
        log.info("list--pageVO:{}", pageVO);
        return R.success(pageVO);
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
        boolean deleteByIds = tCourseService.deleteByIds(ids);
        log.info("delete--result:{}", deleteByIds);
        return R.success(null);
    }

    /**
     * Upload.
     *
     * @param file the file
     */
    @RequestMapping("/upload")
    public Result<Collection<String>> upload(MultipartFile[] file) throws IOException {
        log.info("upload--param file:{}", file);
        if (null == file || file.length < 1) {
            return R.fail(PARAMS_REQUIRED_IS_NULL);
        }
        // check file size
        for (MultipartFile tempFile : file) {
            long size = tempFile.getSize();
            if (size > 50 * 1024 * 1024) {
                return R.fail(FILE_OVERSIZE);
            }
        }
        // download to local
        String projectPath = System.getProperty("user.dir");
        List<File> localFileList = new ArrayList<>();
        for (MultipartFile tempFile : file) {
            String originalFilename = tempFile.getOriginalFilename();
            File destFile = new File(projectPath + File.separator + originalFilename);
            tempFile.transferTo(destFile);
            localFileList.add(destFile);
        }
        Collection<String> ids = tCourseService.upload(localFileList);
        return R.success(ids);
    }
}
