package com.example.springbootdemo.impl;

import com.example.springbootdemo.dto.LoginResponseDTO;
import com.example.springbootdemo.dto.LoginUserDTO;
import com.example.springbootdemo.mapper.UserServiceMapper;
import com.example.springbootdemo.model.Userbase;
import com.example.springbootdemo.service.UserService;
import com.example.springbootdemo.utils.JwtUtil;
import com.example.springbootdemo.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserServiceMapper userServiceMapper;

    @Override
    public LoginResponseDTO login(String username, String password) {
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

        LoginResponseDTO response = new LoginResponseDTO();
        response.setAccessToken(JwtUtil.generateAccessToken(user.getName()));
        response.setRefreshToken(JwtUtil.generateRefreshToken(user.getName()));

        LoginUserDTO loginUser = new LoginUserDTO();
        loginUser.setId(user.getId());
        loginUser.setUsername(user.getName());
        loginUser.setNickname(user.getName());
        response.setUser(loginUser);

        List<String> roles = userServiceMapper.findRoleCodesByUserId(user.getId());
        List<String> permissions = userServiceMapper.findPermissionCodesByUserId(user.getId());
        response.setRoles(roles == null ? List.of() : roles.stream().map(String::toUpperCase).toList());
        response.setPermissions(permissions == null ? List.of() : permissions);

        return response;
    }
}
