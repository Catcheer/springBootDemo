package com.example.springbootdemo.dto;

import lombok.Data;

import java.util.List;

@Data
public class OperationLogQueryDTO {

    private Integer page = 1;
    private Integer pageSize = 10;
    private String username;
    private String module;
    private String operation;
    private List<String> createTime; // 操作时间区间，格式：["2026-07-01 00:00:00","2026-07-06 23:59:59"]
    private Integer status;
}
