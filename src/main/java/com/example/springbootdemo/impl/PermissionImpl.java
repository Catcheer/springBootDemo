package com.example.springbootdemo.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.springbootdemo.common.PageResult;
import com.example.springbootdemo.dto.PermissionCreateDTO;
import com.example.springbootdemo.dto.PermissionQueryDTO;
import com.example.springbootdemo.dto.PermissionUpdateDTO;
import com.example.springbootdemo.mapper.PermissionMapper;
import com.example.springbootdemo.model.Permission;
import com.example.springbootdemo.service.PermissionService;

@Service
public class PermissionImpl implements PermissionService {

    private final PermissionMapper permissionMapper;

    public PermissionImpl(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    @Override
    public PageResult<Permission> listPermissions(PermissionQueryDTO queryDTO) {
        if (queryDTO == null) {
            queryDTO = new PermissionQueryDTO();
        }

        int page = queryDTO.getPage() == null || queryDTO.getPage() < 1 ? 1 : queryDTO.getPage();
        int pageSize = queryDTO.getPageSize() == null || queryDTO.getPageSize() < 1 ? 10 : queryDTO.getPageSize();
        int offset = (page - 1) * pageSize;

        List<Permission> permissions = permissionMapper.findPermissionsByCondition(queryDTO, offset, pageSize);
        long total = permissionMapper.countPermissionsByCondition(queryDTO);
        return new PageResult<>(permissions, page, pageSize, total);
    }

    @Override
    public Permission getPermissionById(Integer id) {
        if (id == null) {
            return null;
        }
        return permissionMapper.findById(id);
    }

    @Override
    public Permission createPermission(PermissionCreateDTO createDTO) {
        if (createDTO == null) {
            return null;
        }

        String permissionName = StringUtils.hasText(createDTO.getPermissionName())
                ? createDTO.getPermissionName().trim()
                : null;
        String permissionCode = StringUtils.hasText(createDTO.getPermissionCode())
                ? createDTO.getPermissionCode().trim()
                : null;

        if (!StringUtils.hasText(permissionName) || !StringUtils.hasText(permissionCode)) {
            return null;
        }

        Permission existing = permissionMapper.findByPermissionCode(permissionCode);
        if (existing != null) {
            return null;
        }

        Permission permission = new Permission();
        permission.setPermissionName(permissionName);
        permission.setPermissionCode(permissionCode);
        permission.setDescription(StringUtils.hasText(createDTO.getDescription()) ? createDTO.getDescription().trim() : null);

        permissionMapper.insertPermission(permission);
        return permissionMapper.findById(permission.getId());
    }

    @Override
    public Permission updatePermission(Integer id, PermissionUpdateDTO updateDTO) {
        if (id == null || updateDTO == null) {
            return null;
        }

        Permission existing = permissionMapper.findById(id);
        if (existing == null) {
            return null;
        }

        String permissionName = StringUtils.hasText(updateDTO.getPermissionName())
                ? updateDTO.getPermissionName().trim()
                : null;
        String permissionCode = StringUtils.hasText(updateDTO.getPermissionCode())
                ? updateDTO.getPermissionCode().trim()
                : null;

        if (!StringUtils.hasText(permissionName) || !StringUtils.hasText(permissionCode)) {
            return null;
        }

        Permission duplicate = permissionMapper.findByPermissionCode(permissionCode);
        if (duplicate != null && !duplicate.getId().equals(id)) {
            return null;
        }

        permissionMapper.updatePermission(
                id,
                permissionName,
                permissionCode,
                StringUtils.hasText(updateDTO.getDescription()) ? updateDTO.getDescription().trim() : null
        );
        return permissionMapper.findById(id);
    }

    @Override
    public boolean deletePermission(Integer id) {
        if (id == null) {
            return false;
        }
        return permissionMapper.deletePermission(id) > 0;
    }
}