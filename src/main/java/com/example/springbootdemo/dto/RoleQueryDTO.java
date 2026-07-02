package com.example.springbootdemo.dto;

import lombok.Data;

@Data
public class RoleQueryDTO {
    private Integer page = 1;
    private Integer pageSize = 10;
    private String roleName;
    private String roleCode;
    private Integer status;
}
