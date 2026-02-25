package com.example.springbootdemo.service;

public interface UserService {
    /**
     * 登录验证，成功返回 JWT token，用户不存在或密码错误返回 null
     */
    String login(String username, String password);
}
