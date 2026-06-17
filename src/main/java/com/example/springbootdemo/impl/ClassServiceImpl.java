package com.example.springbootdemo.impl;

import com.example.springbootdemo.mapper.ClassServiceMapper;
import com.example.springbootdemo.model.ClassVo;
import com.example.springbootdemo.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ClassServiceImpl  implements ClassService {
    @Autowired
    ClassServiceMapper classServiceMapper;

    @Override
    public List<ClassVo> getClassList() {
        System.out.println("Service");
        return  classServiceMapper.getClassList();
    }
}
