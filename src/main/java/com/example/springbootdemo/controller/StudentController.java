package com.example.springbootdemo.controller;

import com.example.springbootdemo.model.Student;
import com.example.springbootdemo.service.StudentService;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/addStudent")
    public void addStudent(@RequestBody Student s){
        studentService.addStudent(s);
    };

    @PostMapping("/updateStudent")
    public void updateStudent(@RequestBody Student s){
        studentService.updateStudent(s);
    };

    @DeleteMapping("/delStudent/{id}")
    public void delStudent(@PathVariable("id") String id){
            studentService.delStudent(id);
    };
}
