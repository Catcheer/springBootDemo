package com.example.springbootdemo.impl;

import com.example.springbootdemo.common.PageResult;
import com.example.springbootdemo.dto.RoleCreateDTO;
import com.example.springbootdemo.dto.RoleQueryDTO;
import com.example.springbootdemo.dto.RoleUpdateDTO;
import com.example.springbootdemo.mapper.RoleServiceMapper;
import com.example.springbootdemo.model.Role;
import com.example.springbootdemo.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleServiceMapper roleServiceMapper;

    public RoleServiceImpl(RoleServiceMapper roleServiceMapper) {
        this.roleServiceMapper = roleServiceMapper;
    }

    @Override
    public PageResult<Role> listRoles(RoleQueryDTO queryDTO) {
        if (queryDTO == null) {
            queryDTO = new RoleQueryDTO();
        }

        int page = queryDTO.getPage() == null || queryDTO.getPage() < 1 ? 1 : queryDTO.getPage();
        int pageSize = queryDTO.getPageSize() == null || queryDTO.getPageSize() < 1 ? 10 : queryDTO.getPageSize();

        String roleName = queryDTO.getRoleName();
        String roleCode = queryDTO.getRoleCode();
        Integer status = queryDTO.getStatus();

        int offset = (page - 1) * pageSize;
        List<Role> roles = roleServiceMapper.findRolesByCondition(roleName, roleCode, status, pageSize, offset);
        long total = roleServiceMapper.countRolesByCondition(roleName, roleCode, status);
        return new PageResult<>(roles, page, pageSize, total);
    }

    @Override
    public Role getRoleById(Integer id) {
        if (id == null) {
            return null;
        }
        return roleServiceMapper.findById(id);
    }

    @Override
    public Role createRole(RoleCreateDTO createDTO) {
        if (createDTO == null || createDTO.getRoleName() == null || createDTO.getRoleCode() == null) {
            return null;
        }

        Role existing = roleServiceMapper.findByRoleCode(createDTO.getRoleCode());
        if (existing != null) {
            return null;
        }

        Role role = new Role();
        role.setRoleName(createDTO.getRoleName());
        role.setRoleCode(createDTO.getRoleCode());
        role.setDescription(createDTO.getDescription());
        // role.setStatus(createDTO.getStatus());

        roleServiceMapper.insertRole(role);
        return roleServiceMapper.findById(role.getId());
    }

    @Override
    public Role updateRole(Integer id, RoleUpdateDTO updateDTO) {
        if (id == null || updateDTO == null) {
            return null;
        }

        Role existing = roleServiceMapper.findById(id);
        if (existing == null) {
            return null;
        }

        roleServiceMapper.updateRole(
                id,
                updateDTO.getRoleName(),
                updateDTO.getRoleCode(),
                updateDTO.getDescription()
                // updateDTO.getStatus()
        );
        return roleServiceMapper.findById(id);
    }

    @Override
    public boolean deleteRole(Integer id) {
        if (id == null) {
            return false;
        }
        return roleServiceMapper.deleteRole(id) > 0;
    }
}
