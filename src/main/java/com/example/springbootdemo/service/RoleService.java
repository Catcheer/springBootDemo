package com.example.springbootdemo.service;

import com.example.springbootdemo.common.PageResult;
import com.example.springbootdemo.model.Role;
import com.example.springbootdemo.dto.RoleCreateDTO;
import com.example.springbootdemo.dto.RoleQueryDTO;
import com.example.springbootdemo.dto.RoleUpdateDTO;

public interface RoleService {
    PageResult<Role> listRoles(RoleQueryDTO queryDTO);

    Role getRoleById(Integer id);

    Role createRole(RoleCreateDTO createDTO);

    Role updateRole(Integer id, RoleUpdateDTO updateDTO);

    boolean deleteRole(Integer id);
}
