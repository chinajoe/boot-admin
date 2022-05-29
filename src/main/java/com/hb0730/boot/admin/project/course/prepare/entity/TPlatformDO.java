package com.hb0730.boot.admin.project.course.prepare.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @description:
 * @author: qiaojinfeng3
 * @date: 2022/5/3 23:05
 */
@Data
@Document(collection = "t_platform")
public class TPlatformDO {
    @Id
    private String id;
    /**
     * 平台名称
     */
    private String name;
    /**
     * 平台用户名
     */
    private String dsUserName;
    /**
     * 平台密码
     */
    private String dsPassword;
    /**
     * 平台地址
     */
    private String dsUrl;
    /**
     * 是否启用
     */
    private Boolean enable;
    /**
     * 平台描述
     */
    private String description;
    /**
     * 创建时间
     */
    private Long createTime;
    /**
     * 更新时间
     */
    private Long updateTime;
}
