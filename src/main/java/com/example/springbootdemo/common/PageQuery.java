package com.example.springbootdemo.common;

import lombok.Data;

@Data
public class PageQuery {

    private Integer page = 1;
    private Integer pageSize = 10;
}
