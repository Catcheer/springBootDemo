package com.example.springbootdemo.dto;

import lombok.Data;

@Data
public class PermissionQueryDTO {
    private Integer page = 1;
    private Integer pageSize = 10;
    private String permissionName;
    private String permissionCode;
}
