package com.example.springbootdemo.model;

import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

// import jakarta.persistence.*;

//@Entity
// @Table(name = "student") // 对应数据库里的 student 表
@Data
public class Student {
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY) // 主键自增
    // @GeneratedValue(strategy = GenerationType.IDENTITY) // 主键自增
    private int id;
    private String name;
    private String gender;
    private String birthday;
    private String phone;
    private int classId;
    private String className;
    private String studentNo;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    @Data
    public static class StudentExcelVO {

        @ExcelProperty("学号")
        private String studentNo;

        @ExcelProperty("姓名")
        private String name;

        @ExcelProperty("性别")
        private Integer gender; // 1: 男, 2: 女

        @ExcelProperty("班级")
        private String className;

        @ExcelProperty("生日")
        private String birthday;

        @ExcelProperty("手机号")
        private String phone;

        @ExcelProperty("班级ID")
        private Integer classId;
    }
}
