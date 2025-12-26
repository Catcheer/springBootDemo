package com.example.springbootdemo.controller;

import com.example.springbootdemo.model.Student;
import com.example.springbootdemo.service.StudentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentController {

    private final StudentService studentService;

    public  StudentController(StudentService studentService){
        this.studentService = studentService;
    }

   @GetMapping("/students")
    public List<Student> listStudents(){
         return studentService.getAllStudents();
    }
}
