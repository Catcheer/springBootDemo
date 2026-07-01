package com.example.springbootdemo.impl;

import com.example.springbootdemo.dto.LoginResponseDTO;
import com.example.springbootdemo.dto.LoginUserDTO;
import com.example.springbootdemo.mapper.UserServiceMapper;
import com.example.springbootdemo.model.Userbase;
import com.example.springbootdemo.service.UserService;
import com.example.springbootdemo.utils.JwtUtil;
import com.example.springbootdemo.utils.PasswordUtil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Value("${file.upload-path}")
    private String uploadPath;

    @Value("${file.upload-url}")
    private String uploadUrl;

    private final UserServiceMapper userServiceMapper;
    private final JwtUtil jwtUtil;


    public UserServiceImpl(UserServiceMapper userServiceMapper, JwtUtil jwtUtil) {
        this.userServiceMapper = userServiceMapper;
        this.jwtUtil = jwtUtil;
    }

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
        response.setAccessToken(jwtUtil.generateAccessToken(user.getName()));
        response.setRefreshToken(jwtUtil.generateRefreshToken(user.getName()));

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

    //头像上传
    @Override
    public String uploadAvatar(String username,    MultipartFile file) {
        if (file == null || file.isEmpty()) {
        throw new RuntimeException("请选择上传文件");
    }

    // 获取原文件名
    String originalFilename = file.getOriginalFilename();

    // 获取后缀
    String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

    // 白名单
    List<String> allowTypes = Arrays.asList(
            ".jpg",
            ".jpeg",
            ".png",
            ".gif",
            ".webp"
    );

    if (!allowTypes.contains(suffix.toLowerCase())) {
        throw new RuntimeException("仅支持 jpg、png、gif、webp 图片");
    }

    // 头像目录
    Path avatarDir = Paths.get(uploadPath, "avatar").toAbsolutePath().normalize();

    try {
        Files.createDirectories(avatarDir);
    } catch (IOException e) {
        throw new RuntimeException("头像目录创建失败", e);
    }

    // UUID 文件名
    String fileName = UUID.randomUUID() + suffix;

    Path dest = avatarDir.resolve(fileName);

    try {
        file.transferTo(dest.toFile());
    } catch (IOException e) {
        throw new RuntimeException("头像上传失败", e);
    }

    String avatarPath = "avatar/" + fileName;
    String avatarUrl = uploadUrl + "/avatar/" + fileName;

    userServiceMapper.updateAvatar(username, avatarPath);
    return avatarUrl;
    }
}
