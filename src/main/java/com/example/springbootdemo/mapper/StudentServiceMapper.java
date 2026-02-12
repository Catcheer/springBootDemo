package com.example.springbootdemo.mapper;

import com.example.springbootdemo.model.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentServiceMapper {

        @Select("""
                            SELECT * FROM `student`;
                        """)
        public List<Student> findAll();

        @Insert("""
                        INSERT INTO `student`
                        (student_no,name,gender,birthday,phone,class_id)
                        VALUES(#{student_no},#{name},#{gender},#{birthday},#{phone},#{class_id});
                        """)
        public void addStudent(Student student);

        @Update("""
                        UPDATE `student`
                        SET name=#{name}
                        SET gender=#{gender}
                        SET birthday=#{birthday}
                        SET phone=#{phone}
                        SET class_id=#{class_id}
                        WHERE id=#{id}
                        """)
        public void updateStudent(Student student);

        @Delete("""
                        DELETE FROM `student` WHERE id=#{id}
                        """)
        void delStudent(String id);
}
