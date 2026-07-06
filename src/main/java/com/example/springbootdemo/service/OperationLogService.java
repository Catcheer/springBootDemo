package com.example.springbootdemo.service;

import com.example.springbootdemo.common.PageResult;
import com.example.springbootdemo.dto.OperationLogQueryDTO;
import com.example.springbootdemo.model.OperationLog;

public interface OperationLogService {

    void save(OperationLog log);

    PageResult<OperationLog> list(OperationLogQueryDTO queryDTO);
}
