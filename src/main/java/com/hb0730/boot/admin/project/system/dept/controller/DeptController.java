package com.hb0730.boot.admin.project.system.dept.controller;

import com.hb0730.boot.admin.annotation.ClassDescribe;
import com.hb0730.boot.admin.annotation.PreAuth;
import com.hb0730.boot.admin.domain.controller.SuperSimpleBaseController;
import com.hb0730.boot.admin.domain.result.R;
import com.hb0730.boot.admin.domain.result.Result;
import com.hb0730.boot.admin.project.system.dept.model.dto.DeptDTO;
import com.hb0730.boot.admin.project.system.dept.model.dto.TreeDeptDTO;
import com.hb0730.boot.admin.project.system.dept.model.entity.DeptEntity;
import com.hb0730.boot.admin.project.system.dept.model.query.DeptParams;
import com.hb0730.boot.admin.project.system.dept.service.IDeptService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * 部门  前端控制器
 *
 * @author bing_huang
 * @since 3.0.0
 */
@RestController
@RequestMapping("/system/dept")
@ClassDescribe("部门管理")
@PreAuth("dept")
public class DeptController extends SuperSimpleBaseController<Long, DeptDTO, DeptParams, DeptEntity> {
    private final IDeptService service;

    public DeptController(IDeptService service) {
        super(service);
        this.service = service;
    }

    /**
     * 获取全部树形组织
     *
     * @return 树形组织
     */
    @GetMapping("/tree/all")
    public Result<Set<TreeDeptDTO>> getDeptTreeAll() {
        DeptParams params = new DeptParams();
        List<DeptDTO> list = service.list(params);
        Set<TreeDeptDTO> treeDept = service.buildTree(list);
        return R.success(treeDept);
    }

    @PostMapping("/tree")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMINISTRATOR','dept:query')")
    public Result<Set<TreeDeptDTO>> getDeptTree(@RequestBody DeptParams params){
        List<DeptDTO> list = service.list(params);
        Set<TreeDeptDTO> treeDept = service.buildTree(list);
        return R.success(treeDept);
    }

}

