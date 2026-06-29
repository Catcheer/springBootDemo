package com.example.springbootdemo.interceptor;

import com.example.springbootdemo.mapper.UserServiceMapper;
import com.example.springbootdemo.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final UserServiceMapper userServiceMapper;

    public JwtInterceptor(UserServiceMapper userServiceMapper) {
        this.userServiceMapper = userServiceMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }

        if (token == null || !JwtUtil.validateToken(token)) {
            writeJson(response, HttpServletResponse.SC_UNAUTHORIZED, "token无效，请重新登录");
            return false;
        }

        String username = JwtUtil.getUsernameFromToken(token);
        List<String> roleCodes = userServiceMapper.findRoleCodesByUsername(username);
        List<String> permissionCodes = userServiceMapper.findPermissionCodesByUsername(username);

        if (!hasPermission(request, roleCodes, permissionCodes)) {
            writeJson(response, HttpServletResponse.SC_FORBIDDEN, "没有权限访问该接口");
            return false;
        }

        request.setAttribute("currentUser", username);
        request.setAttribute("currentRoles", roleCodes == null ? List.of() : roleCodes);
        request.setAttribute("currentPermissions", permissionCodes == null ? List.of() : permissionCodes);
        return true;
    }

    private boolean hasPermission(HttpServletRequest request, List<String> roleCodes, List<String> permissionCodes) {
        String path = request.getRequestURI();
        if (roleCodes != null && roleCodes.stream().anyMatch(role -> "ADMIN".equalsIgnoreCase(role))) {
            return true;
        }

        if (path == null) {
            return true;
        }

        String normalizedPath = path.replaceAll("/+$", "");
        Set<String> permissions = permissionCodes == null ? Set.of() : Set.copyOf(permissionCodes);

        if ("/students".equals(normalizedPath)) {
            return permissions.contains("student:list");
        }
        if ("/students/export".equals(normalizedPath)) {
            return permissions.contains("student:list");
        }
        if ("/addStudent".equals(normalizedPath)) {
            return permissions.contains("student:add");
        }
        if ("/updateStudent".equals(normalizedPath)) {
            return permissions.contains("student:update");
        }
        if (normalizedPath.startsWith("/delStudent")) {
            return permissions.contains("student:delete");
        }

        return true;
    }

    private void writeJson(HttpServletResponse response, int status, String message) throws Exception {
        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> result = new HashMap<>();
        result.put("code", status);
        result.put("message", message);
        result.put("data", null);

        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}
