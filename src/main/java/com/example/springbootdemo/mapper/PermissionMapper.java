package com.example.springbootdemo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.springbootdemo.dto.PermissionQueryDTO;
import com.example.springbootdemo.model.Permission;

@Mapper
public interface PermissionMapper {

    List<Permission> findPermissionsByCondition(
            @Param("query") PermissionQueryDTO query,
            @Param("offset") int offset,
            @Param("size") int size);

    long countPermissionsByCondition(@Param("query") PermissionQueryDTO query);

    @Select("SELECT * FROM `sys_permission` WHERE id = #{id}")
    Permission findById(@Param("id") Integer id);

    @Select("SELECT * FROM `sys_permission` WHERE permission_code = #{permissionCode}")
    Permission findByPermissionCode(@Param("permissionCode") String permissionCode);

    @Insert("INSERT INTO `sys_permission` (permission_name, permission_code, description) VALUES (#{permissionName}, #{permissionCode}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertPermission(Permission permission);

    @Update("UPDATE `sys_permission` SET permission_name = #{permissionName}, permission_code = #{permissionCode}, description = #{description}, update_time = CURRENT_TIMESTAMP WHERE id = #{id}")
    int updatePermission(@Param("id") Integer id,
                         @Param("permissionName") String permissionName,
                         @Param("permissionCode") String permissionCode,
                         @Param("description") String description);

    @Delete("DELETE FROM `sys_permission` WHERE id = #{id}")
    int deletePermission(@Param("id") Integer id);
}
