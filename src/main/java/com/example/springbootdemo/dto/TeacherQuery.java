package com.example.springbootdemo.dto;

import com.example.springbootdemo.common.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TeacherQuery extends PageQuery {

    private String name;
    private String teacherNo;
    private String phone;
    private Integer status;
    private Integer subjectId;
}
