package com.example.springbootdemo.impl;

import com.example.springbootdemo.common.PageResult;
import com.example.springbootdemo.dto.PermissionInfoDTO;
import com.example.springbootdemo.dto.RoleCreateDTO;
import com.example.springbootdemo.dto.RoleQueryDTO;
import com.example.springbootdemo.dto.RoleUpdateDTO;
import com.example.springbootdemo.mapper.RoleServiceMapper;
import com.example.springbootdemo.model.Role;
import com.example.springbootdemo.model.RoleWithPermission;
import com.example.springbootdemo.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        List<RoleWithPermission> roles = roleServiceMapper.findRolesByCondition(roleName, roleCode, status, pageSize, offset);
        long total = roleServiceMapper.countRolesByCondition(roleName, roleCode, status);
        List<Role> list = roles.stream()
                .map(this::mapToRole)
                .toList();
        return new PageResult<>(list, page, pageSize, total);
    }

    @Override
    public Role getRoleById(Integer id) {
        if (id == null) {
            return null;
        }
        Role role = roleServiceMapper.findById(id);
        return role == null ? null : mapToRole(role);
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

        roleServiceMapper.insertRole(role);
        if (createDTO.getPermissions() != null) {
            saveRolePermissions(role.getId(), createDTO.getPermissions());
        }
        return getRoleById(role.getId());
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

        String roleName = updateDTO.getRoleName();
        String roleCode = updateDTO.getRoleCode();
        String description = updateDTO.getDescription();
        Integer status = updateDTO.getStatus();
        List<String> permissions = updateDTO.getPermissions();

        boolean hasRoleInfoUpdate = roleName != null || roleCode != null || description != null || status != null;
        if (!hasRoleInfoUpdate && permissions == null) {
            return getRoleById(id);
        }

        if (hasRoleInfoUpdate) {
            roleServiceMapper.updateRole(id, roleName, roleCode, description, status);
        }
        if (permissions != null) {
            saveRolePermissions(id, permissions);
        }
        return getRoleById(id);
    }

    @Override
    public boolean deleteRole(Integer id) {
        if (id == null) {
            return false;
        }
        roleServiceMapper.deleteRolePermissionsByRoleId(id);
        roleServiceMapper.deleteUserRolesByRoleId(id);
        return roleServiceMapper.deleteRole(id) > 0;
    }

    private void saveRolePermissions(Integer roleId, List<String> permissionCodes) {
        roleServiceMapper.deleteRolePermissionsByRoleId(roleId);
        if (permissionCodes == null || permissionCodes.isEmpty()) {
            return;
        }
        List<Integer> permissionIds = roleServiceMapper.findPermissionIdsByPermissionCodes(permissionCodes);
        for (Integer permissionId : permissionIds) {
            roleServiceMapper.insertRolePermission(roleId, permissionId);
        }
    }

    private Role mapToRole(Role role) {
        Role result = new Role();
        result.setId(role.getId());
        result.setRoleName(role.getRoleName());
        result.setRoleCode(role.getRoleCode());
        result.setDescription(role.getDescription());
        result.setStatus(role.getStatus());
        result.setCreateTime(role.getCreateTime());
        result.setUpdateTime(role.getUpdateTime());
        List<PermissionInfoDTO> permissions = roleServiceMapper.findPermissionsByRoleId(role.getId());
        result.setPermissions(permissions == null ? new ArrayList<>() : permissions);
        return result;
    }

    private Role mapToRole(RoleWithPermission row) {
        Role role = new Role();
        role.setId(row.getId());
        role.setRoleName(row.getRoleName());
        role.setRoleCode(row.getRoleCode());
        role.setDescription(row.getDescription());
        role.setStatus(row.getStatus());
        role.setCreateTime(row.getCreateTime());
        role.setUpdateTime(row.getUpdateTime());
        List<PermissionInfoDTO> permissions = new ArrayList<>();
        if (row.getPermissionCodes() != null && row.getPermissionNames() != null) {
            String[] codes = row.getPermissionCodes().split(",");
            String[] names = row.getPermissionNames().split(",");
            int len = Math.min(codes.length, names.length);
            for (int i = 0; i < len; i++) {
                permissions.add(new PermissionInfoDTO(codes[i], names[i]));
            }
        }
        role.setPermissions(permissions);
        return role;
    }
}
