package com.example.springbootdemo.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.example.springbootdemo.model.Student;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public class ExcelToJdbcService {

    /**
     * 读取本地 Excel 并返回 JSON 字符串
     */
    public String convertExcelToJson(String filePath) throws Exception {
        List<Student> list = EasyExcel.read(filePath)
                .head(Student.class)
                .sheet()
                .doReadSync();
        return JSON.toJSONString(list, JSONWriter.Feature.PrettyFormat);
    }

    /**
     * 读取上传的 Excel 文件 (MultipartFile)
     */
    public List<Student> convertExcelToJson(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            List<Student> list = EasyExcel.read(inputStream)
                    .head(Student.class)
                    .sheet()
                    .doReadSync();

            System.out.println(list.size());
            return list;
        }   catch (Exception e) {
            e.printStackTrace();
            return List.of();
//            return "Error:----------------------- " + e.getMessage();
        }
    }
}