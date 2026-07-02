package com.example.springbootdemo.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RoleWithPermission {
    private Integer id;
    private String roleName;
    private String roleCode;
    private String description;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String permissionCodes;
    private String permissionNames;
}
