package com.example.springbootdemo.dto;

import lombok.Data;
import java.util.List;

@Data
public class StudentQuery {

    // 分页参数
    private Integer page = 1;
    private Integer pageSize = 10;

    // 查询条件
    private String studentNo;
    private String name;
    private String gender;
    private List<String> birthDate; // 日期范围，格式：["2000-01-01","2000-12-31"]
}
