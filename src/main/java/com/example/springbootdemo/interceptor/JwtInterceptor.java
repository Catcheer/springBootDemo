package com.example.springbootdemo.interceptor;

import com.example.springbootdemo.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;

public class JwtInterceptor implements HandlerInterceptor {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {
        // 从请求头中获取 token，并去掉 "Bearer " 前缀
        String authHeader = request.getHeader("Authorization");
        String token = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7); // 去掉 "Bearer " 前缀
        }

        // token 不存在，或校验不通过 → 返回 401
        if (token == null || !JwtUtil.validateToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");

            Map<String, Object> result = new HashMap<>();
            result.put("code", 401);
            result.put("message", "token无效，请重新登录");
            result.put("data", null);

            response.getWriter().write(objectMapper.writeValueAsString(result));
            return false; // 拦截，不继续往下执行
        }

        // token 合法，将用户名存入 request 供后续使用（可选）
        String username = JwtUtil.getUsernameFromToken(token);
        request.setAttribute("currentUser", username);
        return true; // 放行
    }
}
