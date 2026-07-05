package com.example.springbootdemo.impl;

import com.example.springbootdemo.dto.TeacherQuery;
import com.example.springbootdemo.mapper.SubjectServiceMapper;
import com.example.springbootdemo.mapper.TeacherServiceMapper;
import com.example.springbootdemo.model.Teacher;
import com.example.springbootdemo.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    TeacherServiceMapper teacherServiceMapper;

    @Autowired
    SubjectServiceMapper subjectServiceMapper;

    @Override
    public List<Teacher> getTeachers(TeacherQuery query, int page, int size) {
        int offset = (page - 1) * size;
        List<Teacher> teachers = teacherServiceMapper.getTeachers(query, offset, size);
        for (Teacher teacher : teachers) {
            fillSubjects(teacher);
        }
        return teachers;
    }

    @Override
    public long count(TeacherQuery query) {
        return teacherServiceMapper.count(query);
    }

    @Override
    public void addTeacher(Teacher teacher) {
        teacherServiceMapper.addTeacher(teacher);
        saveTeacherSubjects(teacher.getId(), teacher.getSubjects());
    }

    @Override
    public void updateTeacher(Teacher teacher) {
        teacherServiceMapper.updateTeacher(teacher);
        saveTeacherSubjects(teacher.getId(), teacher.getSubjects());
    }

    @Override
    public void delTeacher(int id) {
        subjectServiceMapper.deleteTeacherSubjects(id);
        teacherServiceMapper.delTeacher(id);
    }

    private void fillSubjects(Teacher teacher) {
        String subjectIdsRaw = teacher.getSubjectIdsRaw();
        if (subjectIdsRaw != null && !subjectIdsRaw.isEmpty()) {
            teacher.setSubjects(
                    Arrays.stream(subjectIdsRaw.split(","))
                            .map(String::trim)
                            .map(Integer::parseInt)
                            .collect(Collectors.toList()));
        } else {
            teacher.setSubjects(List.of());
        }
        teacher.setSubjectIdsRaw(null);
        if (teacher.getSubjectsName() == null) {
            teacher.setSubjectsName("");
        }
    }

    private void saveTeacherSubjects(int teacherId, List<Integer> subjectIds) {
        subjectServiceMapper.deleteTeacherSubjects(teacherId);
        if (subjectIds == null || subjectIds.isEmpty()) {
            return;
        }
        for (Integer subjectId : subjectIds) {
            if (subjectId != null) {
                subjectServiceMapper.addTeacherSubject(teacherId, subjectId);
            }
        }
    }
}
