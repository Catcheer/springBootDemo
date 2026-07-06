package com.example.springbootdemo.impl;

import com.example.springbootdemo.common.PageResult;
import com.example.springbootdemo.dto.OperationLogQueryDTO;
import com.example.springbootdemo.mapper.OperationLogMapper;
import com.example.springbootdemo.model.OperationLog;
import com.example.springbootdemo.service.OperationLogService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperationLogServiceImpl implements OperationLogService {

    private final OperationLogMapper operationLogMapper;

    public OperationLogServiceImpl(OperationLogMapper operationLogMapper) {
        this.operationLogMapper = operationLogMapper;
    }

    @Override
    public void save(OperationLog log) {
        if (log == null) {
            return;
        }
        operationLogMapper.insert(log);
    }

    @Override
    public PageResult<OperationLog> list(OperationLogQueryDTO queryDTO) {
        if (queryDTO == null) {
            queryDTO = new OperationLogQueryDTO();
        }

        int page = queryDTO.getPage() == null || queryDTO.getPage() < 1 ? 1 : queryDTO.getPage();
        int pageSize = queryDTO.getPageSize() == null || queryDTO.getPageSize() < 1 ? 10 : queryDTO.getPageSize();
        int offset = (page - 1) * pageSize;

        List<OperationLog> list = operationLogMapper.findByCondition(queryDTO, pageSize, offset);
        long total = operationLogMapper.countByCondition(queryDTO);
        return new PageResult<>(list, page, pageSize, total);
    }
}
