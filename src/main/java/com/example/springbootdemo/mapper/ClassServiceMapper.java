package com.example.springbootdemo.mapper;

import com.example.springbootdemo.dto.ClassQuery;
import com.example.springbootdemo.model.ClassInfo;
import com.example.springbootdemo.model.ClassStudent;
import com.example.springbootdemo.model.ClassVo;
import com.example.springbootdemo.model.TeacherClassAssignmentVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

    List<TeacherClassAssignmentVo> findTeacherClassAssignments(@Param("teacherId") int teacherId);

    @Delete("DELETE FROM `class` WHERE id = #{id}")
    void delClass(int id);
     
    @Select("select c.class_name, COUNT(*) as student_count   from class c JOIN student s ON c.id = s.class_id group by c.class_name;")
     List<ClassStudent> getClassesWithStudentCount();
          
}
