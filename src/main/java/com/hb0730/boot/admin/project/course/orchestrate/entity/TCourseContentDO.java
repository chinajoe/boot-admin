package com.hb0730.boot.admin.project.course.orchestrate.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @description:
 * @author: qiaojinfeng3
 * @date: 2022/5/3 23:05
 */
@Data
@Document(collection = "t_course_content")
public class TCourseContentDO {
    @Id
    private String id;
    // 课程id
    private String fkCourseId;
    // 课程内容
    private String content;
    // 课程html内容
    private String htmlContent;
    // 发布设置
    private Integer publishType;
    // 创建时间
    private Long createTime;
    // 更新时间
    private Long updateTime;
}
