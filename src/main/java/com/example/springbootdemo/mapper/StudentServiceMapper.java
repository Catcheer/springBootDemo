package com.example.springbootdemo.mapper;

import com.example.springbootdemo.model.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentServiceMapper {

    // 分页查询（重点）
    @Select("""
                SELECT *
                FROM student
                LIMIT #{offset}, #{size}
            """)
    List<Student> findAll(
            int offset,
            int size);

    // 总数统计
    @Select("SELECT COUNT(*) FROM student")
    long count();

    // 新增
    @Insert("""
                INSERT INTO student
                (student_no, name, gender, birthday, phone, class_id)
                VALUES
                (#{studentNo}, #{name}, #{gender}, #{birthday}, #{phone}, #{classId})
            """)
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
