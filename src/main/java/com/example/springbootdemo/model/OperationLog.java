package com.example.springbootdemo.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OperationLog {

    private Long id;
    private String username;
    private String module;
    private String operation;
    private String requestMethod;
    private String requestUri;
    private String params;
    private String result;
    private Integer status;
    private Integer executionTime;
    private LocalDateTime createTime;
}
