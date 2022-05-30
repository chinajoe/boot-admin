package com.hb0730.boot.admin.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 上传文件实体类
 * @author: qiaojinfeng3
 * @date : 2022-05-30
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadFileInfo {
    private String id;
    private String name;
    private String url;
}
