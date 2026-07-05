package com.example.springbootdemo.service;

import com.example.springbootdemo.dto.TeacherQuery;
import com.example.springbootdemo.model.Teacher;
import com.example.springbootdemo.model.TeacherOptionVo;

import java.util.List;

public interface TeacherService {

    List<TeacherOptionVo> getAllTeachersWithSubjects();

    List<Teacher> getTeachers(TeacherQuery query, int page, int size);

    long count(TeacherQuery query);

    void addTeacher(Teacher teacher);

    void updateTeacher(Teacher teacher);

    void delTeacher(int id);
}
