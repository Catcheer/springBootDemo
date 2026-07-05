package com.example.springbootdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbootdemo.model.SubjectVo;
import com.example.springbootdemo.service.SubjectService;
import com.example.springbootdemo.common.Result;

@RestController
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @GetMapping("/subjects")
    public Result<List<SubjectVo>> getAllSubjects() {
        return Result.success(subjectService.getAllSubjects());
    }
}
