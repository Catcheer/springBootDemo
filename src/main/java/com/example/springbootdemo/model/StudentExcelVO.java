package com.example.springbootdemo.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class StudentExcelVO {

    @ExcelProperty(value = "学号", index = 0)
    private String studentNo;

    @ExcelProperty(value = "姓名", index = 1)
    private String name;

    @ExcelProperty(value = "性别", index = 2)
    private String gender;

    @ExcelProperty(value = "班级", index = 3)
    private String className;

    @ExcelProperty(value = "生日", index = 4)
    private String birthday;

    @ExcelProperty(value = "手机号", index = 5)
    private String phone;

    @ExcelProperty(value = "班级ID", index = 6)
    private Integer classId;
}