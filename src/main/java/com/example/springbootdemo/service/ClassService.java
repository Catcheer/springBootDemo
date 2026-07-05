package com.example.springbootdemo.service;

import com.example.springbootdemo.dto.ClassQuery;
import com.example.springbootdemo.model.ClassInfo;
import com.example.springbootdemo.model.ClassVo;

import java.util.List;

public interface ClassService {

    List<ClassVo> getClassList();

    List<ClassInfo> getClasses(ClassQuery query, int page, int size);

    long count(ClassQuery query);

    void addClass(ClassInfo classInfo);

    void updateClass(ClassInfo classInfo);

    void delClass(int id);
}
