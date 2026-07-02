package com.example.springbootdemo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LoginUserDTO {
    private Integer id;
    private String username;
    private String nickname;
    private String avatar;
    private String email;
    //手机号
    private String phone;
    private LocalDateTime lastLoginTime;
    private String lastLoginIp;
    private String createTime;
}
