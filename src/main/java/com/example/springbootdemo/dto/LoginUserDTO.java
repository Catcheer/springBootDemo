package com.example.springbootdemo.dto;

import lombok.Data;

@Data
public class LoginUserDTO {
    private Integer id;
    private String username;
    private String nickname;
    private String avatar;
    private String email;
    //手机号
    private String phone;
}
