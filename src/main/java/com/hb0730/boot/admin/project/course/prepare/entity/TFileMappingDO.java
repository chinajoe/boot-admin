package com.hb0730.boot.admin.project.course.prepare.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @description:
 * @author: qiaojinfeng3
 * @date: 2022/5/3 23:05
 */
@Document(collection = "t_file_mapping")
public class TFileMappingDO {
    @Id
    private String id;
    // 素材名称
    private String fileName;
    // 素材类型
    private String fileType;
    // 素材大小
    private String fileSize;
    // 存储key
    private String ossKey;
    // 创建时间
    private Long createTime;
    // 更新时间
    private Long updateTime;
}
