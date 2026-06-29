package com.example.springbootdemo.model;

import lombok.Data;

@Data
public class Userbase {
    private Integer id;
    private String password;
    private String name;
    private String email;
}
