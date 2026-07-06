package com.example.springbootdemo.aspect;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.example.springbootdemo.annotation.OperLog;
import com.example.springbootdemo.common.Result;
import com.example.springbootdemo.model.OperationLog;
import com.example.springbootdemo.service.OperationLogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;
import java.util.Map;

@Aspect
@Component
public class OperLogAspect {

    private static final int MAX_TEXT_LENGTH = 2000;
    private static final String[] SENSITIVE_KEYS = {"password", "oldPassword", "newPassword"};

    private final OperationLogService operationLogService;

    public OperLogAspect(OperationLogService operationLogService) {
        this.operationLogService = operationLogService;
    }

    @Around("@annotation(operLog)")
    public Object around(ProceedingJoinPoint joinPoint, OperLog operLog) throws Throwable {
        long startTime = System.currentTimeMillis();
        HttpServletRequest request = getRequest();

        OperationLog log = new OperationLog();
        log.setModule(operLog.module());
        log.setOperation(operLog.operation());
        log.setUsername(getCurrentUsername(request));
        if (request != null) {
            log.setRequestMethod(request.getMethod());
            log.setRequestUri(request.getRequestURI());
        }
        log.setParams(buildParams(joinPoint));

        try {
            Object result = joinPoint.proceed();
            fillSuccessLog(log, result);
            return result;
        } catch (Throwable ex) {
            log.setStatus(0);
            log.setResult(truncate(ex.getMessage()));
            throw ex;
        } finally {
            log.setExecutionTime((int) (System.currentTimeMillis() - startTime));
            operationLogService.save(log);
        }
    }

    private void fillSuccessLog(OperationLog log, Object result) {
        if (result instanceof Result<?> apiResult) {
            log.setStatus(apiResult.getCode() != null && apiResult.getCode() == 200 ? 1 : 0);
            log.setResult(truncate(JSON.toJSONString(apiResult)));
            return;
        }
        log.setStatus(1);
        log.setResult(truncate(JSON.toJSONString(result)));
    }

    private String buildParams(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return null;
        }

        Map<String, Object> params = new LinkedHashMap<>();
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (shouldSkipArg(arg)) {
                continue;
            }
            params.put("arg" + i, maskSensitiveData(arg));
        }
        return truncate(JSON.toJSONString(params));
    }

    private boolean shouldSkipArg(Object arg) {
        return arg instanceof HttpServletRequest
                || arg instanceof HttpServletResponse
                || arg instanceof MultipartFile;
    }

    private Object maskSensitiveData(Object arg) {
        if (arg == null) {
            return null;
        }
        try {
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(arg));
            for (String key : SENSITIVE_KEYS) {
                if (jsonObject.containsKey(key)) {
                    jsonObject.put(key, "******");
                }
            }
            return jsonObject;
        } catch (Exception ignored) {
            return arg;
        }
    }

    private String getCurrentUsername(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }
        Object username = request.getAttribute("currentUser");
        return username == null ? "unknown" : username.toString();
    }

    private HttpServletRequest getRequest() {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes == null ? null : attributes.getRequest();
    }

    private String truncate(String value) {
        if (value == null) {
            return null;
        }
        return value.length() <= MAX_TEXT_LENGTH ? value : value.substring(0, MAX_TEXT_LENGTH);
    }
}
