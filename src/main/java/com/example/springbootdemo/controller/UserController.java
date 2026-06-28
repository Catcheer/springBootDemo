package com.example.springbootdemo.controller;

import com.example.springbootdemo.common.Result;
import com.example.springbootdemo.dto.UserLogin;
import com.example.springbootdemo.dto.RefreshDTO;
import org.springframework.data.redis.core.RedisTemplate;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import com.example.springbootdemo.service.UserService;
import com.example.springbootdemo.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @PostMapping("/login")
    public Result<Map<String, String>> login(@RequestBody UserLogin userLogin) {
        String userId = userService.login(userLogin.getUsername(), userLogin.getPassword());
        if (userId == null) {
            return Result.error(401, "用户名或密码错误");
        }

        String accessToken = JwtUtil.generateAccessToken(userId);
        String refreshToken = JwtUtil.generateRefreshToken(userId);

        // refreshToken 存 Redis（关键）
        redisTemplate.opsForValue()
                .set("refresh:" + userId, refreshToken, 7, TimeUnit.DAYS);

        return Result.success(Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken
        ));
    }

    @PostMapping("/refresh")
    public Result<Map<String, String>> refresh(@RequestBody RefreshDTO dto) {
        String userId = JwtUtil.parseUserId(dto.getRefreshToken());

        if (userId == null) {
            return Result.error(401, "refreshToken无效");
        }

        String redisToken = redisTemplate.opsForValue()
                .get("refresh:" + userId);

        if (redisToken == null || !dto.getRefreshToken().equals(redisToken)) {
            return Result.error(401, "refreshToken无效");
        }

        String newAccessToken = JwtUtil.generateAccessToken(userId);
        String newRefreshToken = JwtUtil.generateRefreshToken(userId);

        redisTemplate.opsForValue()
                .set("refresh:" + userId, newRefreshToken, 7, TimeUnit.DAYS);

        return Result.success(Map.of(
                "accessToken", newAccessToken,
                "refreshToken", newRefreshToken
        ));
    }

    @GetMapping("/logout")
    public Result<String> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            JwtUtil.invalidateToken(token);
        }
        return Result.success("退出登录成功");
    }
}
