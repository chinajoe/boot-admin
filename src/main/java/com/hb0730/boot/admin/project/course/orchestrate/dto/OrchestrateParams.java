package com.hb0730.boot.admin.project.course.orchestrate.dto;

import com.hb0730.boot.admin.domain.model.query.BaseParams;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @description:
 * @author: qiaojinfeng3
 * @date: 2022/5/4 21:33
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class OrchestrateParams extends BaseParams {
    private String id;
    private String courseName;
    private Integer publishStatus;

    public OrchestrateParams(String id) {
        this.id = id;
    }
}
