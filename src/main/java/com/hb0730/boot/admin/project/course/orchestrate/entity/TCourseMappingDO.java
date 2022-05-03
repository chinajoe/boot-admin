package com.hb0730.boot.admin.project.course.orchestrate.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @description:
 * @author: qiaojinfeng3
 * @date: 2022/5/3 23:05
 */
@Document(collection = "t_course_mapping")
public class TCourseMappingDO {
    @Id
    private String id;
    // 课程id
    private String fkCourseId;
    // 平台id
    private String fkPlatformId;
    // 发布状态未发布，已发布
    private Integer publishStatus;
    // 发布时间
    private Long publishTime;
    // 发布进度
    private String progress;
    // 发布结果成功/失败
    private String result;
    // 创建时间
    private Long createTime;
    // 更新时间
    private Long updateTime;
}
