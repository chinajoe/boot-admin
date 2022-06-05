package com.hb0730.boot.admin.project.course.prepare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * @description:
 * @author: qiaojinfeng3
 * @date: 2022-06-04
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TFileInfoDTO implements Comparable<TFileInfoDTO> {
    private String id;
    private String title;
    private String description;
    private String content;

    @Override
    public int compareTo(@NotNull TFileInfoDTO o) {
        return -this.title.compareTo(o.getTitle());
    }
}
