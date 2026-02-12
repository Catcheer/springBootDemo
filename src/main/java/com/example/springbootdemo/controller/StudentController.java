package com.example.springbootdemo.controller;

import com.example.springbootdemo.common.PageResult;
import com.example.springbootdemo.common.Result;
import com.example.springbootdemo.model.Student;
import com.example.springbootdemo.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public Result<PageResult<Student>> listStudents(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        List<Student> list = studentService.getAllStudents(page, size);
        long total = studentService.count();
        PageResult<Student> pageData = new PageResult<>(list, page, size, total);
        return Result.success(pageData);
    }

    @PostMapping("/addStudent")
    public void addStudent(@RequestBody Student s) {
        studentService.addStudent(s);
    };

    @PostMapping("/updateStudent")
    public void updateStudent(@RequestBody Student s) {
        studentService.updateStudent(s);
    };

    @DeleteMapping("/delStudent/{id}")
    public void delStudent(@PathVariable("id") int id) {
        studentService.delStudent(id);
    };
}
