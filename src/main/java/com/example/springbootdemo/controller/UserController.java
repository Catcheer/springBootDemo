package com.example.springbootdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbootdemo.common.Result;
import com.example.springbootdemo.dto.UserLogin;
import com.example.springbootdemo.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public Result<String> login(@RequestBody UserLogin userLogin) {
        String token = userService.login(userLogin.getUsername(), userLogin.getPassword());
        if (token == null) {
            return Result.error(401, "用户名或密码错误");
        }
        return Result.success(token);
    }
}
