package com.example.springbootdemo.model;

import lombok.Data;

@Data
public class SubjectVo {
    private int id;
    private String subjectCode;
    private String subjectName;
   
    private String createTime;
    private String updateTime;
}
