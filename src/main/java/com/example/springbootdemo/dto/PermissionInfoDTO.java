package com.example.springbootdemo.dto;

import lombok.Data;

@Data
public class PermissionInfoDTO {
    private String permissionCode;
    private String permissionName;

    public PermissionInfoDTO() {
    }

    public PermissionInfoDTO(String permissionCode, String permissionName) {
        this.permissionCode = permissionCode;
        this.permissionName = permissionName;
    }
}
