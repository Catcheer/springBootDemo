package com.example.springbootdemo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.springbootdemo.model.Userbase;

@Mapper
public interface UserServiceMapper {
    @Select("SELECT * FROM `user` WHERE name = #{username} AND password = #{password}")
    Userbase login(@Param("username") String username, @Param("password") String password);
}
