package com.example.springbootdemo.service;

import com.example.springbootdemo.model.Student;
import java.util.List;

public interface StudentService {
    List<Student> getAllStudents();
    void  addStudent(Student s);
    void updateStudent(Student s);

    void delStudent(String id);
}