package com.example.springbootdemo.dto;

import lombok.Data;

@Data
public class UpdateUserDTO {
    private String email;
    private String phone;
    private String nickName;
}
