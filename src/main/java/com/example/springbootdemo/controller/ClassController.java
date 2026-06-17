package com.example.springbootdemo.controller;


import com.example.springbootdemo.common.Result;
import com.example.springbootdemo.model.ClassVo;
import com.example.springbootdemo.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/class")
public class ClassController {
    @Autowired
    ClassService classService;
    @GetMapping("/get")
    public Result<List<ClassVo>>  classList (){
        System.out.println("/class/get");
        List<ClassVo>  classList =   classService.getClassList();
        return  Result.success(classList);
    }


}
