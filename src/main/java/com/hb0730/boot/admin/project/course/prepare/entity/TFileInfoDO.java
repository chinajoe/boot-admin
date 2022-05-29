package com.hb0730.boot.admin.project.course.prepare.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The type T file info do.
 *
 * @description:
 * @author: qiaojinfeng3
 * @date: 2022 /5/3 23:04
 */
@Data
@NoArgsConstructor
@Document(collection = "t_file_info")
public class TFileInfoDO {
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

    /**
     * Instantiates a new T file info do.
     *
     * @param fileName   the file name
     * @param fileType   the file type
     * @param fileSize   the file size
     * @param ossKey     the oss key
     * @param createTime the create time
     * @param updateTime the update time
     */
    public TFileInfoDO(String fileName, String fileType, String fileSize, String ossKey, Long createTime, Long updateTime) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.ossKey = ossKey;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
}
