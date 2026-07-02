package com.example.springbootdemo.controller;

import com.example.springbootdemo.common.PageResult;
import com.example.springbootdemo.common.Result;
import com.example.springbootdemo.dto.PermissionCreateDTO;
import com.example.springbootdemo.dto.PermissionQueryDTO;
import com.example.springbootdemo.dto.PermissionUpdateDTO;
import com.example.springbootdemo.model.Permission;
import com.example.springbootdemo.service.PermissionService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping("/list")
    public Result<PageResult<Permission>> listPermissions(@RequestBody(required = false) PermissionQueryDTO queryDTO) {
        return Result.success(permissionService.listPermissions(queryDTO));
    }

    @GetMapping("/{id}")
    public Result<Permission> getPermissionById(@PathVariable Integer id) {
        Permission permission = permissionService.getPermissionById(id);
        if (permission == null) {
            return Result.error(404, "权限不存在");
        }
        return Result.success(permission);
    }

    @PostMapping("/add")
    public Result<Permission> createPermission(@RequestBody PermissionCreateDTO createDTO) {
        Permission permission = permissionService.createPermission(createDTO);
        if (permission == null) {
            return Result.error(400, "权限名或权限编码不合法，或已存在");
        }
        return Result.success(permission);
    }

    @PutMapping("/update/{id}")
    public Result<Permission> updatePermission(@PathVariable Integer id, @RequestBody PermissionUpdateDTO updateDTO) {
        Permission permission = permissionService.updatePermission(id, updateDTO);
        if (permission == null) {
            return Result.error(404, "权限不存在");
        }
        return Result.success(permission);
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> deletePermission(@PathVariable Integer id) {
        boolean deleted = permissionService.deletePermission(id);
        if (!deleted) {
            return Result.error(404, "权限不存在");
        }
        return Result.success("删除成功");
    }
}
