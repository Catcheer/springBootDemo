package com.example.springbootdemo.mapper;

// import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

// import com.example.springbootdemo.model.DashboardOverview;
import com.example.springbootdemo.model.DashboardStudent;

@Service
public interface DashboardServiceMapper {

    // @Select("SELECT * FROM dashboard_overview")
    // DashboardOverview getDashboardOverview();

    @Select("SELECT * FROM dashboard_student")
    DashboardStudent getDashboardStudent();
    
}
