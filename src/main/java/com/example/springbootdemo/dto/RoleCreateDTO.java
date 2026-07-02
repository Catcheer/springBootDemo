package com.example.springbootdemo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class RoleCreateDTO {
    @JsonAlias({"roleName", "role_name"})
    private String roleName;

    @JsonAlias({"roleCode", "role_code"})
    private String roleCode;

    private String description;
    // private Integer status;
}
