package com.example.springbootdemo.controller;

import com.example.springbootdemo.common.PageResult;
import com.example.springbootdemo.common.Result;
import com.example.springbootdemo.dto.OperationLogQueryDTO;
import com.example.springbootdemo.model.OperationLog;
import com.example.springbootdemo.service.OperationLogService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/operationLog")
public class OperationLogController {

    private final OperationLogService operationLogService;

    public OperationLogController(OperationLogService operationLogService) {
        this.operationLogService = operationLogService;
    }

    @PostMapping("/list")
    public Result<PageResult<OperationLog>> list(@RequestBody(required = false) OperationLogQueryDTO queryDTO) {
        return Result.success(operationLogService.list(queryDTO));
    }
}
