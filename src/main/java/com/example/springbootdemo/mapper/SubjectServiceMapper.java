package com.example.springbootdemo.mapper;

import com.example.springbootdemo.model.SubjectVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SubjectServiceMapper {

    @Select("SELECT * FROM subject")
    List<SubjectVo> selectAllSubjects();

    @Select("""
            SELECT s.*
            FROM subject s
            INNER JOIN teacher_subject ts ON s.id = ts.subject_id
            WHERE ts.teacher_id = #{teacherId}
            """)
    List<SubjectVo> selectSubjectsByTeacherId(@Param("teacherId") int teacherId);

    @Insert("INSERT INTO teacher_subject (teacher_id, subject_id) VALUES (#{teacherId}, #{subjectId})")
    void addTeacherSubject(@Param("teacherId") int teacherId, @Param("subjectId") int subjectId);

    @Delete("DELETE FROM teacher_subject WHERE teacher_id = #{teacherId}")
    void deleteTeacherSubjects(@Param("teacherId") int teacherId);
}
