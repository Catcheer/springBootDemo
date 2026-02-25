package com.example.springbootdemo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserServiceMapper {
    @Select("SELECT * FROM `user` WHERE name = #{username} AND password = #{password}")
    String login(String username, String password);
}
