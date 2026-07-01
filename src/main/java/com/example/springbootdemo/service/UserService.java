package com.example.springbootdemo.service;

import org.springframework.web.multipart.MultipartFile;

import com.example.springbootdemo.dto.LoginResponseDTO;
import com.example.springbootdemo.dto.LoginUserDTO;
import com.example.springbootdemo.dto.UpdateUserDTO;

public interface UserService {
    /**
     * 登录验证，成功返回登录响应数据，用户不存在或密码错误返回 null
     */
    LoginResponseDTO login(String username, String password, String loginIp);

    // 修改当前用户信息
    LoginUserDTO updateUser(String username, UpdateUserDTO updateUserDTO);

    // 头像上传
    String uploadAvatar(String username, MultipartFile file);
}
