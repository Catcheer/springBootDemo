package com.example.springbootdemo.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.example.springbootdemo.model.Student;
import com.example.springbootdemo.model.StudentExcelVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public class ExcelToJdbcService {

    /**
     * 读取本地 Excel 并返回 JSON 字符串
     */
    public String convertExcelToJson(String filePath) throws Exception {
        List<StudentExcelVO> list = EasyExcel.read(filePath)
                .head(StudentExcelVO.class)
                .sheet()
                .doReadSync();
        return JSON.toJSONString(list, JSONWriter.Feature.PrettyFormat);
    }

    /**
     * 读取上传的 Excel 文件 (MultipartFile)
     */
    public List<Student> convertExcelToStudents(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            List<StudentExcelVO> list = EasyExcel.read(inputStream)
                    .head(StudentExcelVO.class)
                    .sheet()
                    .doReadSync();
            return list.stream().map(this::toStudent).toList();
        } catch (Exception e) {
            throw new RuntimeException("Excel 解析失败: " + e.getMessage(), e);
        }
    }

    private Student toStudent(StudentExcelVO vo) {
        Student student = new Student();
        student.setStudentNo(vo.getStudentNo());
        student.setName(vo.getName());
        student.setGender(vo.getGender());
        student.setBirthday(vo.getBirthday());
        student.setPhone(vo.getPhone());
        student.setClassName(vo.getClassName());
        if (vo.getClassId() != null) {
            student.setClassId(vo.getClassId());
        }
        return student;
    }
}