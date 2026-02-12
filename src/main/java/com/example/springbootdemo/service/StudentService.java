package com.example.springbootdemo.service;

import com.example.springbootdemo.dto.StudentQuery;
import com.example.springbootdemo.model.Student;
import java.util.List;

public interface StudentService {
    List<Student> getStudents(StudentQuery query, int page, int size);

    void addStudent(Student s);

    void updateStudent(Student s);

    void delStudent(int id);

    long count(StudentQuery query); // ✅ 新增
}