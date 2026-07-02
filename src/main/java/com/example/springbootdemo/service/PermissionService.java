package com.example.springbootdemo.service;

import com.example.springbootdemo.common.PageResult;
import com.example.springbootdemo.dto.PermissionCreateDTO;
import com.example.springbootdemo.dto.PermissionQueryDTO;
import com.example.springbootdemo.dto.PermissionUpdateDTO;
import com.example.springbootdemo.model.Permission;

public interface PermissionService {

    PageResult<Permission> listPermissions(PermissionQueryDTO queryDTO);

    Permission getPermissionById(Integer id);

    Permission createPermission(PermissionCreateDTO createDTO);

    Permission updatePermission(Integer id, PermissionUpdateDTO updateDTO);

    boolean deletePermission(Integer id);
}
