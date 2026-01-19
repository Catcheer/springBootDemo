package com.example.springbootdemo.mapper;

import com.example.springbootdemo.model.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentServiceMapper {

    @Select("""
                SELECT * FROM `user`;
            """)
    public List<Student> findAll();

    @Insert("""
            INSERT INTO `user`
            (id,name,email,password)
            VALUES(#{id},#{name},#{email},#{password});
            """)
    public void addStudent(Student student);

    @Update("""
            UPDATE `user`
            SET name=#{name}
            WHERE id=#{id}
            """)
    public  void updateStudent(Student student);

    @Delete("""
            DELETE FROM `user` WHERE id=#{id}
            """)
    void delStudent(String id);
}
