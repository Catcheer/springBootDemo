package com.example.springbootdemo.impl;

import com.example.springbootdemo.dto.ClassQuery;
import com.example.springbootdemo.mapper.ClassServiceMapper;
import com.example.springbootdemo.model.ClassInfo;
import com.example.springbootdemo.model.ClassVo;
import com.example.springbootdemo.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    ClassServiceMapper classServiceMapper;

    @Override
    public List<ClassVo> getClassList() {
        return classServiceMapper.getClassList();
    }

    @Override
    public List<ClassInfo> getClasses(ClassQuery query, int page, int size) {
        int offset = (page - 1) * size;
        return classServiceMapper.getClasses(query, offset, size);
    }

    @Override
    public long count(ClassQuery query) {
        return classServiceMapper.count(query);
    }

    @Override
    public void addClass(ClassInfo classInfo) {
        classServiceMapper.addClass(classInfo);
    }

    @Override
    public void updateClass(ClassInfo classInfo) {
        classServiceMapper.updateClass(classInfo);
    }

    @Override
    public void delClass(int id) {
        classServiceMapper.delClass(id);
    }
}
