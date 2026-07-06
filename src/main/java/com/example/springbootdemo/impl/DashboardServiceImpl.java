package com.example.springbootdemo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springbootdemo.dto.ClassQuery;
import com.example.springbootdemo.dto.StudentQuery;
import com.example.springbootdemo.dto.TeacherQuery;
import com.example.springbootdemo.mapper.ClassServiceMapper;
import com.example.springbootdemo.mapper.StudentServiceMapper;
import com.example.springbootdemo.mapper.SubjectServiceMapper;
import com.example.springbootdemo.mapper.TeacherServiceMapper;
import com.example.springbootdemo.model.ClassStudent;
import com.example.springbootdemo.model.DashboardOverview;
import com.example.springbootdemo.model.DashboardStudent;
import com.example.springbootdemo.model.GenderDistribution;
import com.example.springbootdemo.service.DashboardService;

@Service
public class DashboardServiceImpl implements DashboardService {
    @Autowired
    private StudentServiceMapper studentServiceMapper;
    @Autowired
    private TeacherServiceMapper teacherServiceMapper;
    @Autowired
    private SubjectServiceMapper subjectServiceMapper;
    @Autowired
    private ClassServiceMapper classServiceMapper;

    @Override
    public DashboardOverview getDashboard() {
        StudentQuery studentQuery = new StudentQuery();
        TeacherQuery teacherQuery = new TeacherQuery();
        ClassQuery classQuery = new ClassQuery();

        long totalStudents = studentServiceMapper.count(studentQuery);
        long totalTeachers = teacherServiceMapper.count(teacherQuery);
        long totalSubjects = subjectServiceMapper.count();
        long totalClasses = classServiceMapper.count(classQuery);
        DashboardOverview dashboardOverview = new DashboardOverview();
        dashboardOverview.setTotalStudents(totalStudents);
        dashboardOverview.setTotalTeachers(totalTeachers);
        dashboardOverview.setTotalSubjects(totalSubjects);
        dashboardOverview.setTotalClasses(totalClasses);
        return dashboardOverview;
    }

    @Override
    public DashboardStudent getDashboardStudent() {
        List<ClassStudent> classStudents = classServiceMapper.getClassesWithStudentCount();
        List<GenderDistribution> genderDistributions = studentServiceMapper.getGenderDistributions();

        DashboardStudent dashboardStudent = new DashboardStudent();
        dashboardStudent.setClassStudent(classStudents);
        dashboardStudent.setGenderDistribution(genderDistributions);
        return dashboardStudent;
    }
}
