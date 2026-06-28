package com.example.springbootdemo.mapper;

import com.example.springbootdemo.model.Userbase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserServiceMapper {
    @Select("SELECT * FROM `user` WHERE name = #{username}")
    Userbase findByUsername(@Param("username") String username);
}
