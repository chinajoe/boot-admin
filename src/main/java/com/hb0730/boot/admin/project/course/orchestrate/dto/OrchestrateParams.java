package com.hb0730.boot.admin.project.course.orchestrate.dto;

import com.hb0730.boot.admin.domain.model.query.BaseParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description:
 * @author: qiaojinfeng3
 * @date: 2022/5/4 21:33
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OrchestrateParams extends BaseParams {
    private String courseName;
}
