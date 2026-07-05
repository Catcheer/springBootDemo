package com.example.springbootdemo.mapper;

import com.example.springbootdemo.dto.ClassQuery;
import com.example.springbootdemo.model.ClassInfo;
import com.example.springbootdemo.model.ClassVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ClassServiceMapper {

    List<ClassVo> getClassList();

    List<ClassInfo> getClasses(
            @Param("query") ClassQuery query,
            @Param("offset") int offset,
            @Param("size") int size);

    long count(@Param("query") ClassQuery query);

    void addClass(ClassInfo classInfo);

    void updateClass(ClassInfo classInfo);

    @Delete("DELETE FROM `class` WHERE id = #{id}")
    void delClass(int id);
}
