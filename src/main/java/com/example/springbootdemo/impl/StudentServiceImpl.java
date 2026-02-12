package com.example.springbootdemo.impl;

import com.example.springbootdemo.mapper.StudentServiceMapper;
import com.example.springbootdemo.model.Student;
//import com.example.springbootdemo.repository.StudentRepository;
import com.example.springbootdemo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    // private final StudentRepository studentRepository;
    @Autowired
    StudentServiceMapper studentServiceMapper;

    // public StudentServiceImpl(StudentRepository studentRepository){
    // this.studentRepository = studentRepository;
    // }

    @Override
    public List<Student> getAllStudents(int page, int size) {
        int offset = (page - 1) * size;
        return studentServiceMapper.findAll(offset, size);
    }

    @Override
    public void addStudent(Student s) {
        studentServiceMapper.addStudent(s);
    }

    @Override
    public void updateStudent(Student s) {
        studentServiceMapper.updateStudent(s);
    };

    @Override
    public void delStudent(int id) {
        studentServiceMapper.delStudent(id);
    };

    @Override
    public long count() {
        return studentServiceMapper.count();
    }
}