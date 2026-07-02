package com.example.springbootdemo.service;

import com.example.springbootdemo.common.PageResult;
import com.example.springbootdemo.dto.LoginResponseDTO;
import com.example.springbootdemo.dto.LoginUserDTO;
import com.example.springbootdemo.dto.UpdateUserDTO;
import com.example.springbootdemo.dto.UserCreateDTO;
import com.example.springbootdemo.dto.UserQueryDTO;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    /**
     * 登录验证，成功返回登录响应数据，用户不存在或密码错误返回 null
     */
    LoginResponseDTO login(String username, String password, String loginIp);

    // 修改当前用户信息
    LoginUserDTO updateUser(String username, UpdateUserDTO updateUserDTO);

    // 头像上传
    String uploadAvatar(String username, MultipartFile file);

    // 管理端用户列表
    PageResult<LoginUserDTO> listUsers(UserQueryDTO queryDTO);

    // 管理端查询单个用户
    LoginUserDTO getUserById(Integer id);

    // 管理端新增用户
    LoginUserDTO createUser(UserCreateDTO createUserDTO);

    // 管理端修改用户
    LoginUserDTO updateUserById(Integer id, UpdateUserDTO updateUserDTO);

    // 管理端删除用户
    boolean deleteUser(Integer id);
}
