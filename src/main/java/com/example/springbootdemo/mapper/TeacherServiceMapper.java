package com.example.springbootdemo.mapper;

import com.example.springbootdemo.dto.TeacherQuery;
import com.example.springbootdemo.model.Teacher;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TeacherServiceMapper {

    List<Teacher> getTeachers(
            @Param("query") TeacherQuery query,
            @Param("offset") int offset,
            @Param("size") int size);

    long count(@Param("query") TeacherQuery query);

    void addTeacher(Teacher teacher);

    void updateTeacher(Teacher teacher);

    @Delete("DELETE FROM teacher WHERE id = #{id}")
    void delTeacher(int id);
}
