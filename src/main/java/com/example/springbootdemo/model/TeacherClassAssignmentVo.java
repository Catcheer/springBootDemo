package com.example.springbootdemo.model;

import lombok.Data;

@Data
public class TeacherClassAssignmentVo {

    private int classId;
    private String className;
    /** chinese / math / english */
    private String roleType;
    private String roleName;
}
