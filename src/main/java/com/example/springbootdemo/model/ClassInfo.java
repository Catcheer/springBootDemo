package com.example.springbootdemo.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClassInfo {

    private int id;
    private String className;
    private Integer headTeacherId;
    private Integer chineseTeacherId;
    private Integer mathTeacherId;
    private Integer englishTeacherId;
    private Integer status;
    private String description;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    private String headTeacherName;
    private String chineseTeacherName;
    private String mathTeacherName;
    private String englishTeacherName;
}
