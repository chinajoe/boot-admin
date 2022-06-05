package com.hb0730.boot.admin.project.course.orchestrate.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @description:
 * @author: qiaojinfeng3
 * @date: 2022/5/4 21:35
 */
@Data
@ToString
public class CourseContentAddDTO implements Serializable {
    private String id;
    // 课程id
    private String fkCourseId;
    // 课程内容
    private String content;
    // 课程html内容
    private String htmlContent;
    // 发布设置
    private Integer publishType;
}
