package com.example.springbootdemo.controller;

import com.example.springbootdemo.common.Result;
import com.example.springbootdemo.model.Student;
import com.example.springbootdemo.service.StudentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.springbootdemo.utils.ExcelToJdbcService;

import java.util.List;

@RestController
public class UploadController {

    private final StudentService studentService;

    public UploadController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/uploadExcel")
    public Result<Integer> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            List<Student> list = new ExcelToJdbcService().convertExcelToJson(file);

            // 循环 单条插入
            for (Student student : list) {

                studentService.addStudent(student);
            }

            return Result.success(list.size());
        } catch (Exception e) {
            e.printStackTrace();
            // return "Error: " + e.getMessage();
            return Result.success();
        }
    }
}
