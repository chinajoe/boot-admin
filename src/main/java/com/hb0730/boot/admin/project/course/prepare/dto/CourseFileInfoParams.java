package com.hb0730.boot.admin.project.course.prepare.dto;

import com.hb0730.boot.admin.domain.model.query.BaseParams;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * The type Course file info params.
 *
 * @description:
 * @author: qiaojinfeng3
 * @date: 2022 /5/4 21:33
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CourseFileInfoParams extends BaseParams {
    private String id;
    private Integer latestDays;

    /**
     * Instantiates a new Course file info params.
     *
     * @param id the id
     */
    public CourseFileInfoParams(String id) {
        this.id = id;
    }

    /**
     * Instantiates a new Course file info params.
     *
     * @param latestDays the latest days
     */
    public CourseFileInfoParams(Integer latestDays, Long pageSize) {
        setPageSize(pageSize);
        this.latestDays = latestDays;
    }
}
