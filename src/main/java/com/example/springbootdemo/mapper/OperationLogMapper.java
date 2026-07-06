package com.example.springbootdemo.mapper;

import com.example.springbootdemo.dto.OperationLogQueryDTO;
import com.example.springbootdemo.model.OperationLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OperationLogMapper {

    int insert(OperationLog log);

    List<OperationLog> findByCondition(@Param("query") OperationLogQueryDTO query,
                                       @Param("pageSize") int pageSize,
                                       @Param("offset") int offset);

    long countByCondition(@Param("query") OperationLogQueryDTO query);
}
