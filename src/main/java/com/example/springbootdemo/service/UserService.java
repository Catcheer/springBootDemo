package com.example.springbootdemo.service;

import com.example.springbootdemo.dto.LoginResponseDTO;

public interface UserService {
    /**
     * 登录验证，成功返回登录响应数据，用户不存在或密码错误返回 null
     */
    LoginResponseDTO login(String username, String password);
}
