package com.example.springbootdemo.controller;

import com.example.springbootdemo.common.PageResult;
import com.example.springbootdemo.common.Result;
import com.example.springbootdemo.dto.StudentQuery;
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

    @PostMapping("/students")
    public Result<PageResult<Student>> listStudents(@RequestBody StudentQuery query) {
        int page = query.getPage();
        int pageSize = query.getPageSize();
        List<Student> list = studentService.getStudents(query, page, pageSize);
        long total = studentService.count(query);
        PageResult<Student> pageData = new PageResult<>(list, page, pageSize, total);
        return Result.success(pageData);
    }

    @PostMapping("/addStudent")
    public Result<Integer> addStudent(@RequestBody Student s) {
        studentService.addStudent(s);
        int id = s.getId();
        return Result.success(id);
    };

    @PostMapping("/updateStudent")
    public Result<Integer> updateStudent(@RequestBody Student s) {
        studentService.updateStudent(s);
        return Result.success(s.getId());
    };

    @DeleteMapping("/delStudent/{id}")
    public Result<Integer> delStudent(@PathVariable("id") int id) {
        studentService.delStudent(id);
        return Result.success(id);
    };
}
