package com.example.springbootdemo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springbootdemo.mapper.UserServiceMapper;
import com.example.springbootdemo.model.Userbase;
import com.example.springbootdemo.service.UserService;
import com.example.springbootdemo.utils.JwtUtil;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserServiceMapper userServiceMapper;

    @Override
    public String login(String username, String password) {
        // 查询数据库，验证用户名+密码
        Userbase user = userServiceMapper.login(username, password);
        if (user == null) {
            // 用户不存在或密码错误
            return null;
        }
        // 登录成功，生成 JWT token（以用户名作为 subject）
        return JwtUtil.generateToken(user.getName());
    }
}
