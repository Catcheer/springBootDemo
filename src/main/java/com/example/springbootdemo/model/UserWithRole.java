package com.example.springbootdemo.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserWithRole {
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String avatar;
    private String nickName;
    private LocalDateTime lastLoginTime;
    private String lastLoginIp;
    private String createTime;
    private String roleCodes;
    private String roleNames;
}
