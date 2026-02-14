package com.example.springbootdemo.mapper;

import com.example.springbootdemo.dto.StudentQuery;
import com.example.springbootdemo.model.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentServiceMapper {

    // 分页 + 条件
    List<Student> getStudents(
            @Param("query") StudentQuery query,
            @Param("offset") int offset,
            @Param("size") int size);

    // 总数
    long count(@Param("query") StudentQuery query);

    // 新增
    void addStudent(Student student);

    // 修改
    @Update("""
                UPDATE student
                SET
                    name = #{name},
                    gender = #{gender},
                    birthday = #{birthday},
                    phone = #{phone},
                    class_id = #{classId}
                WHERE id = #{id}
            """)
    void updateStudent(Student student);

    // 删除
    @Delete("DELETE FROM student WHERE id = #{id}")
    void delStudent(int id);
}
