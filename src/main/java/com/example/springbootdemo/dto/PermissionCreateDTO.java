package com.example.springbootdemo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class PermissionCreateDTO {
    @JsonAlias({"permissionName", "permission_name"})
    private String permissionName;

    @JsonAlias({"permissionCode", "permission_code"})
    private String permissionCode;

    private String description;
}
