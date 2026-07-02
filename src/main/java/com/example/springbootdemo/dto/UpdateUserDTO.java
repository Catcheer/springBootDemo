package com.example.springbootdemo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.List;

@Data
public class UpdateUserDTO {
    private String email;
    private String phone;
    @JsonAlias({"nickname", "nickName"})
    private String nickName;
    private List<String> roles;
}
