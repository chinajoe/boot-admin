package com.hb0730.boot.admin.project.course.orchestrate.dto;

import com.hb0730.boot.admin.domain.model.dto.UploadFileInfo;
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
public class OrchestrateAddDTO implements Serializable {
    // 课程音频
    private UploadFileInfo courseAudio;
    // 课程名称
    private String courseName;
    // 课程封面
    private UploadFileInfo courseCover;
    // 课程描述
    private String courseDescription;
    // 发布状态: 未发布，已发布
    private Integer publishStatus;
    // 发布时间
    private Long publishTime;
}
