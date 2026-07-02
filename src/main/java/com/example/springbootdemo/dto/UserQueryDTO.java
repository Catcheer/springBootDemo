package com.example.springbootdemo.dto;

import lombok.Data;

@Data
public class UserQueryDTO {
    private Integer page = 1;
    private Integer pageSize = 10;
    private String username;
    private String email;
    private String phone;
    private String nickName;
}
