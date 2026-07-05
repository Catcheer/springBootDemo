package com.example.springbootdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Teacher {

    private int id;
    private String teacherNo;
    private String name;
    private Integer gender;
    private String phone;
    /** 任教科目 ID 列表，如 [1, 2] */
    private List<Integer> subjects;
    /** 任教科目名称拼接，如 "语文,数学" */
    private String subjectsName;
    private String email;
    private Integer status;
    private String description;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    /** SQL 聚合字段，仅用于列表查询映射，不返回给前端 */
    @JsonIgnore
    private String subjectIdsRaw;
}
