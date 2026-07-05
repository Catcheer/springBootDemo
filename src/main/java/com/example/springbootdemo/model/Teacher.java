package com.example.springbootdemo.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Teacher {

    private int id;
    private String teacherNo;
    private String name;
    private Integer gender;
    private String phone;
    private String email;
    private Integer status;
    private String description;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
