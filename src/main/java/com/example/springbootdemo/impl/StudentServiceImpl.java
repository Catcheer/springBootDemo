package com.example.springbootdemo.impl;

import com.example.springbootdemo.model.Student;
import com.example.springbootdemo.repository.StudentRepository;
import com.example.springbootdemo.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> getAllStudents(){
        // 调用 JPA 提供的方法直接查询数据库
        return studentRepository.findAll();
    }
}