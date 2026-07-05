package com.example.springbootdemo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.springbootdemo.model.SubjectVo;
import com.example.springbootdemo.model.Teacher;

@Service
public interface SubjectService {
    List<SubjectVo> getAllSubjects();

    // void addTeacherSubject(int teacherId,int  subjectId);
}
