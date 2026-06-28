package com.example.springbootdemo.impl;

import com.example.springbootdemo.mapper.UserServiceMapper;
import com.example.springbootdemo.model.Userbase;
import com.example.springbootdemo.service.UserService;
import com.example.springbootdemo.utils.JwtUtil;
import com.example.springbootdemo.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserServiceMapper userServiceMapper;

    @Override
    public String login(String username, String password) {
        if (username == null || password == null) {
            return null;
        }

        Userbase user = userServiceMapper.findByUsername(username);
        if (user == null) {
            return null;
        }

        if (!PasswordUtil.matchesPassword(password, user.getPassword())) {
            return null;
        }

        return JwtUtil.generateToken(user.getName());
    }
}
