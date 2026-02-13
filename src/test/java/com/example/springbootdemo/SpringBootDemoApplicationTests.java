package com.example.springbootdemo;

import com.example.springbootdemo.dto.StudentQuery;
import com.example.springbootdemo.mapper.StudentServiceMapper;
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
}
