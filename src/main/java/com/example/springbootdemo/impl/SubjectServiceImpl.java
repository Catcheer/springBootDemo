package com.example.springbootdemo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springbootdemo.mapper.SubjectServiceMapper;
import com.example.springbootdemo.model.SubjectVo;
import com.example.springbootdemo.service.SubjectService;

@Service
public class SubjectServiceImpl implements SubjectService{
    @Autowired
   private SubjectServiceMapper subjectServiceMapper;

   @Override
   public List<SubjectVo> getAllSubjects() {
    return subjectServiceMapper.selectAllSubjects();


   }

    @Override
    public long count() {
        return subjectServiceMapper.count();
    }

//    @Override
//    public void addTeacherSubject(SubjectVo subject) {
//        subjectServiceMapper.addTeacherSubject(int tId, int subId)
       
//    }
}
