package com.example.springbootdemo.controller;

import com.example.springbootdemo.annotation.OperLog;
import com.example.springbootdemo.common.PageResult;
import com.example.springbootdemo.common.Result;
import com.example.springbootdemo.dto.TeacherQuery;
import com.example.springbootdemo.model.Teacher;
import com.example.springbootdemo.model.TeacherOptionVo;
import com.example.springbootdemo.service.TeacherService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/teacher/get")
    public Result<List<TeacherOptionVo>> getAllTeachers() {
        return Result.success(teacherService.getAllTeachersWithSubjects());
    }

    @PostMapping("/teachers")
    public Result<PageResult<Teacher>> listTeachers(@RequestBody TeacherQuery query) {
        int page = query.getPage();
        int pageSize = query.getPageSize();
        List<Teacher> list = teacherService.getTeachers(query, page, pageSize);
        long total = teacherService.count(query);
        PageResult<Teacher> pageData = new PageResult<>(list, page, pageSize, total);
        return Result.success(pageData);
    }

    @OperLog(module = "教师管理", operation = "新增")
    @PostMapping("/addTeacher")
    public Result<Integer> addTeacher(@RequestBody Teacher teacher) {
        teacherService.addTeacher(teacher);
        return Result.success(teacher.getId());
    }

    @OperLog(module = "教师管理", operation = "修改")
    @PostMapping("/updateTeacher")
    public Result<Integer> updateTeacher(@RequestBody Teacher teacher) {
        teacherService.updateTeacher(teacher);
        return Result.success(teacher.getId());
    }

    @OperLog(module = "教师管理", operation = "删除")
    @DeleteMapping("/delTeacher/{id}")
    public Result<Integer> delTeacher(@PathVariable("id") int id) {
        teacherService.delTeacher(id);
        return Result.success(id);
    }
}
