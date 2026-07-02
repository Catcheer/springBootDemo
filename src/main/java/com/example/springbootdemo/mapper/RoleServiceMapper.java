package com.example.springbootdemo.mapper;

import com.example.springbootdemo.model.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface RoleServiceMapper {
    List<Role> findRolesByCondition(@Param("roleName") String roleName,
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

    @Insert("INSERT INTO `sys_role` (role_name, role_code, description) VALUES (#{roleName}, #{roleCode}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertRole(Role role);

    @Update("UPDATE `sys_role` SET role_name = #{roleName}, role_code = #{roleCode}, description = #{description}, update_time = CURRENT_TIMESTAMP WHERE id = #{id}")
    int updateRole(@Param("id") Integer id,
                   @Param("roleName") String roleName,
                   @Param("roleCode") String roleCode,
                   @Param("description") String description,
                   @Param("status") Integer status);

    @Delete("DELETE FROM `sys_role` WHERE id = #{id}")
    int deleteRole(@Param("id") Integer id);
}
