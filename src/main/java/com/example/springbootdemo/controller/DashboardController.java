package com.example.springbootdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbootdemo.common.Result;
import com.example.springbootdemo.service.DashboardService;

import com.example.springbootdemo.model.DashboardStudent;
import com.example.springbootdemo.model.DashboardOverview;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;
    @GetMapping("/overview")
    public Result<DashboardOverview> getDashboard() {

        try {
            DashboardOverview dashboardOverview = dashboardService.getDashboard();
            return Result.success(dashboardOverview);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/studentStatistics")
    public Result<DashboardStudent> getDashboardStudent() {
        return Result.success(dashboardService.getDashboardStudent());
    }
}
