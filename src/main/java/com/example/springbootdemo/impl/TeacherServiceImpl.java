package com.example.springbootdemo.impl;

import com.example.springbootdemo.dto.TeacherQuery;
import com.example.springbootdemo.mapper.TeacherServiceMapper;
import com.example.springbootdemo.model.Teacher;
import com.example.springbootdemo.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    TeacherServiceMapper teacherServiceMapper;

    @Override
    public List<Teacher> getTeachers(TeacherQuery query, int page, int size) {
        int offset = (page - 1) * size;
        return teacherServiceMapper.getTeachers(query, offset, size);
    }

    @Override
    public long count(TeacherQuery query) {
        return teacherServiceMapper.count(query);
    }

    @Override
    public void addTeacher(Teacher teacher) {
        teacherServiceMapper.addTeacher(teacher);
    }

    @Override
    public void updateTeacher(Teacher teacher) {
        teacherServiceMapper.updateTeacher(teacher);
    }

    @Override
    public void delTeacher(int id) {
        teacherServiceMapper.delTeacher(id);
    }
}
