package com.example.springbootdemo;

import com.example.springbootdemo.dto.StudentQuery;
import com.example.springbootdemo.mapper.StudentServiceMapper;
import com.example.springbootdemo.utils.ExcelToJdbcService;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest

class SpringBootDemoApplicationTests {

  @Autowired
  private StudentServiceMapper studentServiceMapper;

  @Test
  public void test1() {
    System.out.println("test1");
    studentServiceMapper.getStudents(new StudentQuery(), 0, 10);
  }

  @Test
  public void readExcel() {
    try {
      ExcelToJdbcService service = new ExcelToJdbcService();
      String path = "D:\\test\\springBootDemo\\src\\main\\resources\\test.xlsx";
      String jsonResult = service.convertExcelToJson(path);
      System.out.println("解析后的 JSON 数据：");
      System.out.println(jsonResult);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}
