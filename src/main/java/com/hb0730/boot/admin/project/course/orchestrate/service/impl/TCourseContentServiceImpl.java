package com.hb0730.boot.admin.project.course.orchestrate.service.impl;

import com.hb0730.boot.admin.infrastructure.TCourseContentRepository;
import com.hb0730.boot.admin.project.course.orchestrate.convert.TCourseContentConvert;
import com.hb0730.boot.admin.project.course.orchestrate.dto.CourseContentAddDTO;
import com.hb0730.boot.admin.project.course.orchestrate.entity.TCourseContentDO;
import com.hb0730.boot.admin.project.course.orchestrate.service.TCourseContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: qiaojinfeng3
 * @date: 2022/5/4 22:58
 */
@Service
@RequiredArgsConstructor
public class TCourseContentServiceImpl implements TCourseContentService {
    private final TCourseContentRepository tCourseContentRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public TCourseContentDO add(CourseContentAddDTO addDTO) {
        if (null == addDTO) {
            return null;
        }
        final TCourseContentDO tCourseContentDO = TCourseContentConvert.INSTANCE.toDo(addDTO);
        return tCourseContentRepository.save(tCourseContentDO);
    }
}
