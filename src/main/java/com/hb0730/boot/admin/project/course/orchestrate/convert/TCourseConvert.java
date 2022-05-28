package com.hb0730.boot.admin.project.course.orchestrate.convert;

import com.hb0730.boot.admin.commons.utils.JdDateTimeUtil;
import com.hb0730.boot.admin.project.course.orchestrate.dto.OrchestrateAddDTO;
import com.hb0730.boot.admin.project.course.orchestrate.dto.TCourseDTO;
import com.hb0730.boot.admin.project.course.orchestrate.entity.TCourseDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
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
    default List<TCourseDTO> toDto(List<TCourseDO> list){
        if ( list == null ) {
            return null;
        }

        List<TCourseDTO> list1 = new ArrayList<>( list.size() );
        for ( TCourseDO tCourseDO : list ) {
            list1.add( tCourseDOToTCourseDTO( tCourseDO ) );
        }

        return list1;
    }

    private TCourseDTO tCourseDOToTCourseDTO(TCourseDO tCourseDO) {
        if ( tCourseDO == null ) {
            return null;
        }

        TCourseDTO tCourseDTO = new TCourseDTO();

        tCourseDTO.setId( tCourseDO.getId() );
        tCourseDTO.setCourseName( tCourseDO.getCourseName() );
        tCourseDTO.setCourseCover( tCourseDO.getCourseCover() );
        tCourseDTO.setCourseDescription( tCourseDO.getCourseDescription() );
        tCourseDTO.setPublishStatus( tCourseDO.getPublishStatus() );
        if ( tCourseDO.getPublishTime() != null ) {

            tCourseDTO.setPublishTime( JdDateTimeUtil.formatDateTime(tCourseDO.getPublishTime()) );
        }
        if ( tCourseDO.getCreateTime() != null ) {
            tCourseDTO.setCreateTime( JdDateTimeUtil.formatDateTime( tCourseDO.getCreateTime() ) );
        }
        if ( tCourseDO.getUpdateTime() != null ) {
            tCourseDTO.setUpdateTime( JdDateTimeUtil.formatDateTime( tCourseDO.getUpdateTime() ) );
        }

        return tCourseDTO;
    }

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
