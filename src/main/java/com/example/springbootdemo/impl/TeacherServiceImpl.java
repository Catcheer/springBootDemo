package com.example.springbootdemo.impl;

import com.example.springbootdemo.dto.TeacherQuery;
import com.example.springbootdemo.exception.BusinessException;
import com.example.springbootdemo.mapper.ClassServiceMapper;
import com.example.springbootdemo.mapper.SubjectServiceMapper;
import com.example.springbootdemo.mapper.TeacherServiceMapper;
import com.example.springbootdemo.model.SubjectVo;
import com.example.springbootdemo.model.Teacher;
import com.example.springbootdemo.model.TeacherClassAssignmentVo;
import com.example.springbootdemo.model.TeacherOptionVo;
import com.example.springbootdemo.model.TeacherSubjectVo;
import com.example.springbootdemo.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {

    private static final Map<String, String> ROLE_SUBJECT_KEYWORD = Map.of(
            "chinese", "语文",
            "math", "数学",
            "english", "英语"
    );

    @Autowired
    TeacherServiceMapper teacherServiceMapper;

    @Autowired
    SubjectServiceMapper subjectServiceMapper;

    @Autowired
    ClassServiceMapper classServiceMapper;

    @Override
    public List<TeacherOptionVo> getAllTeachersWithSubjects() {
        List<Teacher> teachers = teacherServiceMapper.getAllTeachers();
        return teachers.stream().map(teacher -> {
            TeacherOptionVo vo = new TeacherOptionVo();
            vo.setId(teacher.getId());
            vo.setTeacherNo(teacher.getTeacherNo());
            vo.setName(teacher.getName());
            List<SubjectVo> subjectList = subjectServiceMapper.selectSubjectsByTeacherId(teacher.getId());
            vo.setSubject(subjectList.stream().map(this::toTeacherSubjectVo).toList());
            return vo;
        }).toList();
    }

    private TeacherSubjectVo toTeacherSubjectVo(SubjectVo subject) {
        TeacherSubjectVo item = new TeacherSubjectVo();
        item.setSubjectId(subject.getId());
        item.setSubjectCode(subject.getSubjectCode());
        item.setSubjectName(subject.getSubjectName());
        return item;
    }

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
        List<Integer> subjectIds = teacher.getSubjects();
        if (subjectIds == null) {
            subjectIds = subjectServiceMapper.selectSubjectsByTeacherId(teacher.getId())
                    .stream()
                    .map(SubjectVo::getId)
                    .toList();
            teacher.setSubjects(subjectIds);
        }
        validateTeacherSubjectsForClassAssignment(teacher.getId(), subjectIds);
        teacherServiceMapper.updateTeacher(teacher);
        saveTeacherSubjects(teacher.getId(), subjectIds);
    }

    @Override
    public void delTeacher(int id) {
        subjectServiceMapper.deleteTeacherSubjects(id);
        teacherServiceMapper.delTeacher(id);
    }

    private void validateTeacherSubjectsForClassAssignment(int teacherId, List<Integer> newSubjectIds) {
        List<TeacherClassAssignmentVo> assignments = classServiceMapper.findTeacherClassAssignments(teacherId);
        if (assignments.isEmpty()) {
            return;
        }

        List<Integer> subjectIds = newSubjectIds != null ? newSubjectIds : List.of();
        List<SubjectVo> selectedSubjects = subjectIds.isEmpty()
                ? List.of()
                : subjectServiceMapper.selectSubjectsByIds(subjectIds);

        for (TeacherClassAssignmentVo assignment : assignments) {
            String keyword = ROLE_SUBJECT_KEYWORD.get(assignment.getRoleType());
            boolean matched = selectedSubjects.stream()
                    .anyMatch(subject -> matchesSubjectRole(subject, keyword));

            if (!matched) {
                throw new BusinessException(400,
                        String.format("该教师已被班级【%s】任命为%s，修改后不再具备对应任教科目（%s），请先在班级管理中调整关联",
                                assignment.getClassName(),
                                assignment.getRoleName(),
                                keyword));
            }
        }
    }

    private boolean matchesSubjectRole(SubjectVo subject, String keyword) {
        if (subject.getSubjectName() != null && subject.getSubjectName().contains(keyword)) {
            return true;
        }
        if (subject.getSubjectCode() == null) {
            return false;
        }
        String code = subject.getSubjectCode().toUpperCase();
        return switch (keyword) {
            case "语文" -> code.contains("CHINESE") || code.contains("CN");
            case "数学" -> code.contains("MATH");
            case "英语" -> code.contains("ENGLISH") || code.contains("EN");
            default -> false;
        };
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
