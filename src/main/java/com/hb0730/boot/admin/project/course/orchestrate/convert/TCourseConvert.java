package com.hb0730.boot.admin.project.course.orchestrate.convert;

import com.hb0730.boot.admin.project.course.orchestrate.dto.OrchestrateAddDTO;
import com.hb0730.boot.admin.project.course.orchestrate.dto.TCourseDTO;
import com.hb0730.boot.admin.project.course.orchestrate.entity.TCourseDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * The interface T course convert.
 *
 * @description:
 * @author: qiaojinfeng3
 * @date: 2022 /5/4 22:02
 */
@Mapper
public interface TCourseConvert {
    /**
     * The constant INSTANCE.
     */
    TCourseConvert INSTANCE = Mappers.getMapper(TCourseConvert.class);

    /**
     * To dto list.
     *
     * @param list the list
     * @return the list
     */
    List<TCourseDTO> toDto(List<TCourseDO> list);

    /**
     * To do t course do.
     *
     * @param addDTO the add dto
     * @return the t course do
     */
    @Mapping(target = "createTime", expression = "java(System.currentTimeMillis())")
    @Mapping(target = "updateTime", expression = "java(System.currentTimeMillis())")
    TCourseDO toDo(OrchestrateAddDTO addDTO);
}
