package com.example.springbootdemo.model;

import lombok.Data;

import java.util.List;

@Data
public class TeacherOptionVo {

    private int id;
    private String teacherNo;
    private String name;
    private List<TeacherSubjectVo> subject;
}
