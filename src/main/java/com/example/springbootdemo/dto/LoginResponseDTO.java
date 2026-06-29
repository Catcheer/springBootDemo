package com.example.springbootdemo.dto;

import lombok.Data;

import java.util.List;

@Data
public class LoginResponseDTO {
    private String accessToken;
    private String refreshToken;
    private LoginUserDTO user;
    private List<String> roles;
    private List<String> permissions;
}
