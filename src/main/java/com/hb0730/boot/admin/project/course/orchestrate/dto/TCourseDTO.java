package com.hb0730.boot.admin.project.course.orchestrate.dto;

import com.hb0730.boot.admin.domain.model.dto.UploadFileInfo;
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
    private UploadFileInfo courseCover;
    // 课程音频
    private UploadFileInfo courseAudio;
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
