package com.example.springbootdemo.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class StudentExcelVO {

    @ExcelProperty("学号")
    private String studentNo;

    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("性别")
    private String gender;

    @ExcelProperty("班级")
    private String className;

    @ExcelProperty("生日")
    private String birthday;

    @ExcelProperty("手机号")
    private String phone;

    @ExcelProperty("班级ID")
    private Integer classId;
}