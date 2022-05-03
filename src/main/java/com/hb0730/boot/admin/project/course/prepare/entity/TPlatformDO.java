package com.hb0730.boot.admin.project.course.prepare.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @description:
 * @author: qiaojinfeng3
 * @date: 2022/5/3 23:05
 */
@Document(collection = "t_platform")
public class TPlatformDO {
    @Id
    private String id;
    // 平台id
    private String fkPlatformId;
    // 素材id
    private String fkFileId;
    // 素材存储key
    private String fkFileOssKey;
    // 映射id
    private String mappingId;
    // 创建时间
    private Long createTime;
    // 更新时间
    private Long updateTime;
}
