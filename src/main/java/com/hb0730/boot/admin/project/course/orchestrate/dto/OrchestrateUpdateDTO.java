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
public class OrchestrateUpdateDTO implements Serializable {
    // 课程id
    private String id;
    // 课程音频
    private String fkCourseAudioId;
    // 课程名称
    private String courseName;
    // 课程封面
    private String courseCover;
    // 课程描述
    private String courseDescription;

}
