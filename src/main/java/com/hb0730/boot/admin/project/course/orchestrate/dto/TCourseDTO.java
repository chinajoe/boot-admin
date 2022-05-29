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
    private String courseCoverName;
    // 课程封面url
    private String courseCoverUrl;
    // 课程音频
    private String courseAudioName;
    // 课程音频url
    private String courseAudioUrl;
    // 课程描述
    private String courseDescription;
    // 发布状态: 未发布，已发布
    private Integer publishStatus;
    // 发布时间
    private String publishTime;
    // 创建时间
    private String createTime;
    // 更新时间
    private String updateTime;
}
