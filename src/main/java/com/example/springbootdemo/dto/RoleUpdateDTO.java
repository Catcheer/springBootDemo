package com.example.springbootdemo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.List;

@Data
public class RoleUpdateDTO {
    @JsonAlias({"roleName", "role_name"})
    private String roleName;

    @JsonAlias({"roleCode", "role_code"})
    private String roleCode;

    private String description;
    private Integer status;
    private List<String> permissions;
}
