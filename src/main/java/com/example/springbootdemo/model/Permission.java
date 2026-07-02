package com.example.springbootdemo.model;

import lombok.Data;

@Data
public class Permission {
    private Integer id;
    private String permissionName;
    private String permissionCode;
    private String description;
    // private Integer status;
    
}
