package com.example.springbootdemo.controller;

import com.example.springbootdemo.common.Result;
import com.example.springbootdemo.dto.LoginResponseDTO;
import com.example.springbootdemo.dto.UserLogin;
import com.example.springbootdemo.dto.RefreshDTO;
import org.springframework.data.redis.core.RedisTemplate;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import com.example.springbootdemo.service.UserService;
import com.example.springbootdemo.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final RedisTemplate<String, String> redisTemplate;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, RedisTemplate<String, String> redisTemplate, JwtUtil jwtUtil) {
        this.userService = userService;
        this.redisTemplate = redisTemplate;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public Result<LoginResponseDTO> login(@RequestBody UserLogin userLogin) {
        LoginResponseDTO loginResponse = userService.login(userLogin.getUsername(), userLogin.getPassword());

        if (loginResponse == null) {
            return Result.error(401, "用户名或密码错误");
        }

        String userName = loginResponse.getUser().getUsername();
        redisTemplate.opsForValue()
                .set("refresh:" + userName, loginResponse.getRefreshToken(), 7, TimeUnit.DAYS);

        return Result.success(loginResponse);
    }

    @PostMapping("/refresh")
    public Result<Map<String, String>> refresh(@RequestBody RefreshDTO dto) {
        String userName = jwtUtil.parseUserId(dto.getRefreshToken());

        if (userName == null) {
            return Result.error(401, "refreshToken无效");
        }

        String redisToken = redisTemplate.opsForValue()
                .get("refresh:" + userName);

        if (redisToken == null || !dto.getRefreshToken().equals(redisToken)) {
            return Result.error(401, "refreshToken无效");
        }

        String newAccessToken = jwtUtil.generateAccessToken(userName);
        String newRefreshToken = jwtUtil.generateRefreshToken(userName);

        redisTemplate.opsForValue()
                .set("refresh:" + userName, newRefreshToken, 7, TimeUnit.DAYS);

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
            jwtUtil.invalidateToken(token);
        }
        return Result.success("退出登录成功");
    }
}
