package com.hb0730.boot.admin.project.course.orchestrate.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @description:
 * @author: qiaojinfeng3
 * @date: 2022/5/3 23:06
 */
@Data
@Document(collection = "t_course")
public class TCourseDO {
    @Id
    private String id;
    // 课程音频
    private String fkCourseAudioId;
    // 课程名称
    private String courseName;
    // 课程封面
    private String courseCover;
    // 课程描述
    private String courseDescription;
    // 发布状态: 未发布=0，发布中=1，已发布=2
    private Integer publishStatus;
    // 发布时间
    private Long publishTime;
    // 创建时间
    private Long createTime;
    // 更新时间
    private Long updateTime;
}
