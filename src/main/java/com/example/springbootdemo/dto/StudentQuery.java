package com.example.springbootdemo.dto;

import lombok.Data;

@Data
public class StudentQuery {

    private String studentNo;
    private String name;
    private String gender;

    // 以后要加条件，往这加
}
