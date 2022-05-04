package com.hb0730.boot.admin.project.course.orchestrate.convert;

import com.hb0730.boot.admin.project.course.orchestrate.dto.CourseContentAddDTO;
import com.hb0730.boot.admin.project.course.orchestrate.entity.TCourseContentDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * The interface T course convert.
 *
 * @description:
 * @author: qiaojinfeng3
 * @date: 2022 /5/4 22:02
 */
@Mapper
public interface TCourseContentConvert {
    /**
     * The constant INSTANCE.
     */
    TCourseContentConvert INSTANCE = Mappers.getMapper(TCourseContentConvert.class);

    /**
     * To do t course do.
     *
     * @param addDTO the add dto
     * @return the t course do
     */
    @Mapping(target = "createTime", expression = "java(System.currentTimeMillis())")
    @Mapping(target = "updateTime", expression = "java(System.currentTimeMillis())")
    TCourseContentDO toDo(CourseContentAddDTO addDTO);
}
