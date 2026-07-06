package com.example.springbootdemo.service;

// import org.springframework.stereotype.Service;

import com.example.springbootdemo.model.DashboardOverview;
import com.example.springbootdemo.model.DashboardStudent;



public interface DashboardService {

    
    DashboardOverview getDashboard();
    DashboardStudent getDashboardStudent();
}
