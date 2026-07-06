package com.example.springbootdemo.controller;

import com.example.springbootdemo.annotation.OperLog;
import com.example.springbootdemo.common.PageResult;
import com.example.springbootdemo.common.Result;
import com.example.springbootdemo.dto.RoleCreateDTO;
import com.example.springbootdemo.dto.RoleQueryDTO;
import com.example.springbootdemo.dto.RoleUpdateDTO;
import com.example.springbootdemo.model.Role;
import com.example.springbootdemo.service.RoleService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/list")
    public Result<PageResult<Role>> listRoles(@RequestBody(required = false) RoleQueryDTO queryDTO) {
        return Result.success(roleService.listRoles(queryDTO));
    }

    @GetMapping("/{id}")
    public Result<Role> getRoleById(@PathVariable Integer id) {
        Role role = roleService.getRoleById(id);
        if (role == null) {
            return Result.error(404, "角色不存在");
        }
        return Result.success(role);
    }

    @OperLog(module = "角色管理", operation = "新增")
    @PostMapping("/add")
    public Result<Role> createRole(@RequestBody RoleCreateDTO createDTO) {
        Role role = roleService.createRole(createDTO);
        if (role == null) {
            return Result.error(400, "角色名或角色编码不合法，或已存在");
        }
        return Result.success(role);
    }

    @OperLog(module = "角色管理", operation = "修改")
    @PutMapping("/update/{id}")
    public Result<Role> updateRole(@PathVariable Integer id, @RequestBody RoleUpdateDTO updateDTO) {
        Role role = roleService.updateRole(id, updateDTO);
        if (role == null) {
            return Result.error(404, "角色不存在");
        }
        return Result.success(role);
    }

    @OperLog(module = "角色管理", operation = "删除")
    @DeleteMapping("/delete/{id}")
    public Result<String> deleteRole(@PathVariable Integer id) {
        boolean deleted = roleService.deleteRole(id);
        if (!deleted) {
            return Result.error(404, "角色不存在");
        }
        return Result.success("删除成功");
    }
}
