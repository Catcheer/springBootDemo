package com.example.springbootdemo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springbootdemo.mapper.UserServiceMapper;
import com.example.springbootdemo.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserServiceMapper userServiceMapper;

    @Override
    public String login(String username, String password) {
        String res = userServiceMapper.login(username, password);
        System.out.println(res);
        return "登录成功111";
    }
}
