package com.example.springbootdemo.controller;

import com.example.springbootdemo.common.PageResult;
import com.example.springbootdemo.common.Result;
import com.example.springbootdemo.dto.StudentQuery;
import com.example.springbootdemo.model.Student;
import com.example.springbootdemo.model.StudentExcelVO;
import com.example.springbootdemo.service.StudentService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import com.alibaba.excel.EasyExcel;

import java.util.List;

@RestController
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/students")
    public Result<PageResult<Student>> listStudents(@RequestBody StudentQuery query) {
        System.out.println("/studentsController");
        int page = query.getPage();
        int pageSize = query.getPageSize();
        List<Student> list = studentService.getStudents(query, page, pageSize);
        long total = studentService.count(query);
        PageResult<Student> pageData = new PageResult<>(list, page, pageSize, total);
        return Result.success(pageData);
    }

    @PostMapping("/addStudent")
    public Result<Integer> addStudent(@RequestBody Student s) {
        studentService.addStudent(s);
        int id = s.getId();
        return Result.success(id);
    };

    @PostMapping("/updateStudent")
    public Result<Integer> updateStudent(@RequestBody Student s) {
        studentService.updateStudent(s);
        return Result.success(s.getId());
    };

    @DeleteMapping("/delStudent/{id}")
    public Result<Integer> delStudent(@PathVariable("id") int id) {
        studentService.delStudent(id);
        return Result.success(id);
    };


    @PostMapping("/students/export")
    public void exportStudents(
            @RequestBody StudentQuery query,
            HttpServletResponse response) throws Exception {

        List<Student> list =
                studentService.getStudentsForExport(query);

        List<StudentExcelVO> excelList = list.stream()
                .map(s -> {
                    StudentExcelVO vo = new StudentExcelVO();

                    vo.setStudentNo(s.getStudentNo());
                    vo.setName(s.getName());
                    vo.setGender(s.getGender());
                    vo.setBirthday(
                            s.getBirthday() == null
                                    ? ""
                                    : s.getBirthday().toString());

                    vo.setPhone(s.getPhone());
                    vo.setClassId(s.getClassId());
                    vo.setClassName(s.getClassName());

                    return vo;
                })
                .toList();

        String fileName = "学生列表.xlsx";

        response.setContentType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        response.setCharacterEncoding("utf-8");

        response.setHeader(
                "Content-Disposition",
                "attachment;filename=" +
                        java.net.URLEncoder.encode(fileName, "UTF-8"));

        EasyExcel.write(
                        response.getOutputStream(),
                        StudentExcelVO.class)
                .sheet("学生信息")
                .doWrite(excelList);
    }


}
