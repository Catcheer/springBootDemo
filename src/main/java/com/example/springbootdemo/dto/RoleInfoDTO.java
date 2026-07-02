package com.example.springbootdemo.dto;

import lombok.Data;

@Data
public class RoleInfoDTO {
    private String roleCode;
    private String roleName;

    public RoleInfoDTO() {
    }

    public RoleInfoDTO(String roleCode, String roleName) {
        this.roleCode = roleCode;
        this.roleName = roleName;
    }
}
