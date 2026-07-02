package com.example.springbootdemo.mapper;

import com.example.springbootdemo.dto.PermissionInfoDTO;
import com.example.springbootdemo.model.Role;
import com.example.springbootdemo.model.RoleWithPermission;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleServiceMapper {
    List<RoleWithPermission> findRolesByCondition(@Param("roleName") String roleName,
                                                  @Param("roleCode") String roleCode,
                                                  @Param("status") Integer status,
                                                  @Param("pageSize") Integer pageSize,
                                                  @Param("offset") Integer offset);

    long countRolesByCondition(@Param("roleName") String roleName,
                               @Param("roleCode") String roleCode,
                               @Param("status") Integer status);

    @Select("SELECT * FROM `sys_role` WHERE id = #{id}")
    Role findById(@Param("id") Integer id);

    @Select("SELECT * FROM `sys_role` WHERE role_code = #{roleCode}")
    Role findByRoleCode(@Param("roleCode") String roleCode);

    @Select("SELECT sp.permission_code AS permissionCode, sp.permission_name AS permissionName FROM `sys_role_permission` srp JOIN `sys_permission` sp ON srp.permission_id = sp.id WHERE srp.role_id = #{roleId}")
    List<PermissionInfoDTO> findPermissionsByRoleId(@Param("roleId") Integer roleId);

    @Select({"<script>",
            "SELECT sp.id FROM `sys_permission` sp",
            "WHERE sp.permission_code IN",
            "<foreach item='code' collection='permissionCodes' open='(' separator=',' close=')'>",
            "#{code}",
            "</foreach>",
            "</script>"})
    List<Integer> findPermissionIdsByPermissionCodes(@Param("permissionCodes") List<String> permissionCodes);

    @Delete("DELETE FROM `sys_role_permission` WHERE role_id = #{roleId}")
    int deleteRolePermissionsByRoleId(@Param("roleId") Integer roleId);

    @Delete("DELETE FROM `sys_user_role` WHERE role_id = #{roleId}")
    int deleteUserRolesByRoleId(@Param("roleId") Integer roleId);

    @Insert("INSERT INTO `sys_role_permission` (role_id, permission_id) VALUES (#{roleId}, #{permissionId})")
    void insertRolePermission(@Param("roleId") Integer roleId, @Param("permissionId") Integer permissionId);

    @Insert("INSERT INTO `sys_role` (role_name, role_code, description) VALUES (#{roleName}, #{roleCode}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertRole(Role role);

    int updateRole(@Param("id") Integer id,
                   @Param("roleName") String roleName,
                   @Param("roleCode") String roleCode,
                   @Param("description") String description,
                   @Param("status") Integer status);

    @Delete("DELETE FROM `sys_role` WHERE id = #{id}")
    int deleteRole(@Param("id") Integer id);
}
