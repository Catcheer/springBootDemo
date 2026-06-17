package com.example.springbootdemo.mapper;


import com.example.springbootdemo.model.ClassVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ClassServiceMapper {
    @Select("""
    SELECT
        id,
        class_name AS className
    FROM `class`
""")
    List<ClassVo> getClassList();

}
