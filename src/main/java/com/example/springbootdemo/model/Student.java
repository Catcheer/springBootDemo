package com.example.springbootdemo.model;

import java.time.LocalDateTime;

import lombok.Data;

// import jakarta.persistence.*;

//@Entity
// @Table(name = "student") // 对应数据库里的 student 表
@Data
public class Student {
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY) // 主键自增
    // @GeneratedValue(strategy = GenerationType.IDENTITY) // 主键自增
    private int id;
    private String name;
    private String gender;
    private String birthday;
    private String phone;
    private int classId;
    private String studentNo;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
