package com.hb0730.boot.admin.project.course.orchestrate.dto;

import lombok.Data;

/**
 * @description:
 * @author: qiaojinfeng3
 * @date: 2022/5/3 23:06
 */
@Data
public class TCourseDTO {
    private String id;
    // 课程名称
    private String courseName;
    // 课程封面
    private String courseCover;
    // 课程描述
    private String courseDescription;
    // 发布状态: 未发布，已发布
    private Integer publishStatus;
    // 发布时间
    private Long publishTime;
    // 创建时间
    private Long createTime;
    // 更新时间
    private Long updateTime;
}
