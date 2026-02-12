package com.example.springbootdemo.common;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {

    // 当前页数据
    private List<T> list;

    // 当前页码（从1开始）
    private Integer page;

    // 每页条数
    private Integer size;

    // 总条数
    private Long total;
}
