package com.example.springbootdemo.dto;

import lombok.Data;

@Data
public class UserCreateDTO {
    private String username;
    private String password;
    private String email;
    private String phone;
    private String nickName;
    private String avatar;
}
