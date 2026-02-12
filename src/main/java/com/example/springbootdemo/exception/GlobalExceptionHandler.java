package com.example.springbootdemo.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.springbootdemo.common.Result;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 捕获所有异常
     */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {

        // 线上可以换成 log.error
        e.printStackTrace();

        return Result.error(500, "系统异常，请联系管理员");
    }
}
