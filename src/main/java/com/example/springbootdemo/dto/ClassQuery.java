package com.example.springbootdemo.dto;

import com.example.springbootdemo.common.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClassQuery extends PageQuery {

    private String className;
    private String headTeacherName;
    private Integer status;
}
