package com.example.springbootdemo.mapper;

import com.example.springbootdemo.model.Userbase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface UserServiceMapper {
    @Select("SELECT * FROM `sys_user` WHERE name = #{username}")
    Userbase findByUsername(@Param("username") String username);

    @Select("SELECT sr.role_code FROM `sys_user_role` sur JOIN `sys_role` sr ON sur.role_id = sr.id WHERE sur.user_id = #{userId}")
    List<String> findRoleCodesByUserId(@Param("userId") Integer userId);

    @Select("SELECT sr.role_code FROM `sys_user_role` sur JOIN `sys_role` sr ON sur.role_id = sr.id JOIN `sys_user` su ON sur.user_id = su.id WHERE su.name = #{username}")
    List<String> findRoleCodesByUsername(@Param("username") String username);

    @Select("SELECT sp.permission_code FROM `sys_user_role` sur JOIN `sys_role_permission` srp ON sur.role_id = srp.role_id JOIN `sys_permission` sp ON srp.permission_id = sp.id WHERE sur.user_id = #{userId}")
    List<String> findPermissionCodesByUserId(@Param("userId") Integer userId);

    @Select("SELECT sp.permission_code FROM `sys_user` su JOIN `sys_user_role` sur ON su.id = sur.user_id JOIN `sys_role_permission` srp ON sur.role_id = srp.role_id JOIN `sys_permission` sp ON srp.permission_id = sp.id WHERE su.name = #{username}")
    List<String> findPermissionCodesByUsername(@Param("username") String username);

    @Update("UPDATE `sys_user` SET avatar = #{avatarPath} WHERE name = #{username}")
    void updateAvatar(@Param("username") String username, @Param("avatarPath") String avatarPath);

    @Update("UPDATE `sys_user` SET last_login_time = #{lastLoginTime}, last_login_ip = #{lastLoginIp} WHERE name = #{username}")
    void updateLoginInfo(@Param("username") String username,
                         @Param("lastLoginTime") LocalDateTime lastLoginTime,
                         @Param("lastLoginIp") String lastLoginIp);
}
