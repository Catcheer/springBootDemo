package com.example.springbootdemo.model;

import java.util.List;

import lombok.Data;



/**
 * DashboardStudent
 * 
 * {
 *  classStudetn:[
 *      {
 *          className: "Class 1",
 *          totalStudents: 100
 *      },
 *      {
 *          className: "Class 2",
 *          totalStudents: 200
 *      }
 *  ],
 * genderDistribution: [
 *      {
 *          gender: "Male",
 *          totalStudents: 100
 *      },
 *      {
 *          gender: "Female",
 *          totalStudents: 200
 *      }
 *  ]
 * }
 */






/**
 * DashboardStudent
 */
@Data
public class DashboardStudent {
    
    private List<ClassStudent> classStudent;
    private List<GenderDistribution> genderDistribution;
    
}