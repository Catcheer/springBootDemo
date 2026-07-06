package com.example.springbootdemo.controller;

import com.example.springbootdemo.annotation.OperLog;
import com.example.springbootdemo.common.PageResult;
import com.example.springbootdemo.common.Result;
import com.example.springbootdemo.dto.ClassQuery;
import com.example.springbootdemo.model.ClassInfo;
import com.example.springbootdemo.model.ClassVo;
import com.example.springbootdemo.service.ClassService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClassController {

    private final ClassService classService;

    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @GetMapping("/class/get")
    public Result<List<ClassVo>> classList() {
        List<ClassVo> classList = classService.getClassList();
        return Result.success(classList);
    }

    @PostMapping("/classes")
    public Result<PageResult<ClassInfo>> listClasses(@RequestBody ClassQuery query) {
        int page = query.getPage();
        int pageSize = query.getPageSize();
        List<ClassInfo> list = classService.getClasses(query, page, pageSize);
        long total = classService.count(query);
        PageResult<ClassInfo> pageData = new PageResult<>(list, page, pageSize, total);
        return Result.success(pageData);
    }

    @OperLog(module = "班级管理", operation = "新增")
    @PostMapping("/addClass")
    public Result<Integer> addClass(@RequestBody ClassInfo classInfo) {
        classService.addClass(classInfo);
        return Result.success(classInfo.getId());
    }

    @OperLog(module = "班级管理", operation = "修改")
    @PostMapping("/updateClass")
    public Result<Integer> updateClass(@RequestBody ClassInfo classInfo) {
        classService.updateClass(classInfo);
        return Result.success(classInfo.getId());
    }

    @OperLog(module = "班级管理", operation = "删除")
    @DeleteMapping("/delClass/{id}")
    public Result<Integer> delClass(@PathVariable("id") int id) {
        classService.delClass(id);
        return Result.success(id);
    }
}
