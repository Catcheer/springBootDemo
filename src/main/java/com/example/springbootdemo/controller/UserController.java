package com.example.springbootdemo.controller;

import com.example.springbootdemo.annotation.OperLog;
import com.example.springbootdemo.common.PageResult;
import com.example.springbootdemo.common.Result;
import com.example.springbootdemo.dto.LoginResponseDTO;
import com.example.springbootdemo.dto.LoginUserDTO;
import com.example.springbootdemo.dto.RefreshDTO;
import com.example.springbootdemo.dto.UpdateUserDTO;
import com.example.springbootdemo.dto.UserCreateDTO;
import com.example.springbootdemo.dto.UserLogin;
import com.example.springbootdemo.dto.UserQueryDTO;
import com.example.springbootdemo.service.UserService;
import com.example.springbootdemo.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final RedisTemplate<String, String> redisTemplate;
    private final JwtUtil jwtUtil;
    private final Environment env;

    public UserController(UserService userService, RedisTemplate<String, String> redisTemplate, JwtUtil jwtUtil, Environment env) {
        this.userService = userService;
        this.redisTemplate = redisTemplate;
        this.jwtUtil = jwtUtil;
        this.env = env;
    }

    @PostMapping("/login")
    public Result<LoginResponseDTO> login(@RequestBody UserLogin userLogin, HttpServletRequest request) {
        String loginIp = getClientIp(request);
        LoginResponseDTO loginResponse = userService.login(userLogin.getUsername(), userLogin.getPassword(), loginIp);

        if (loginResponse == null) {
            return Result.error(401, "用户名或密码错误");
        }

        String userName = loginResponse.getUser().getUsername();
        try {
            log.info("RedisConnectionFactory class={}, spring.redis.host={}, SPRING_REDIS_HOST={}",
                    redisTemplate.getConnectionFactory() != null ? redisTemplate.getConnectionFactory().getClass().getName() : "null",
                    env.getProperty("spring.redis.host"), env.getProperty("SPRING_REDIS_HOST"));
        } catch (Exception e) {
            log.warn("Failed to log Redis info", e);
        }

        redisTemplate.opsForValue()
                .set("refresh:" + userName, loginResponse.getRefreshToken(), 7, TimeUnit.DAYS);

        return Result.success(loginResponse);
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip != null && !ip.isBlank()) {
            return ip.split(",")[0].trim();
        }
        ip = request.getHeader("X-Real-IP");
        if (ip != null && !ip.isBlank()) {
            return ip;
        }
        return request.getRemoteAddr();
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

    @PostMapping("/upload/avatar")
    public Result<String> uploadAvatar(@RequestParam MultipartFile file, HttpServletRequest request) {
        String userName = getUsernameFromRequest(request);
        String avatarUrl = userService.uploadAvatar(userName, file);
        return Result.success(avatarUrl);
    }

    @PostMapping("/update")
    public Result<LoginUserDTO> updateUser(@RequestBody UpdateUserDTO updateUserDTO, HttpServletRequest request) {
        String userName = getUsernameFromRequest(request);
        LoginUserDTO updatedUser = userService.updateUser(userName, updateUserDTO);
        if (updatedUser == null) {
            return Result.error(404, "用户不存在或未登录");
        }
        return Result.success(updatedUser);
    }

    @PostMapping("/list")
    public Result<PageResult<LoginUserDTO>> listUsers(@RequestBody(required = false) UserQueryDTO queryDTO) {
        return Result.success(userService.listUsers(queryDTO));
    }

    @GetMapping("/{id}")
    public Result<LoginUserDTO> getUserById(@PathVariable Integer id) {
        LoginUserDTO user = userService.getUserById(id);
        if (user == null) {
            return Result.error(404, "用户不存在");
        }
        return Result.success(user);
    }

    @OperLog(module = "用户管理", operation = "新增")
    @PostMapping("/add")
    public Result<LoginUserDTO> createUser(@RequestBody UserCreateDTO createUserDTO) {
        LoginUserDTO createdUser = userService.createUser(createUserDTO);
        if (createdUser == null) {
            return Result.error(400, "用户名已存在或参数不合法");
        }
        return Result.success(createdUser);
    }

    @OperLog(module = "用户管理", operation = "修改")
    @PutMapping("/update/{id}")
    public Result<LoginUserDTO> updateUserById(@PathVariable Integer id, @RequestBody UpdateUserDTO updateUserDTO) {
        LoginUserDTO updatedUser = userService.updateUserById(id, updateUserDTO);
        if (updatedUser == null) {
            return Result.error(404, "用户不存在");
        }
        return Result.success(updatedUser);
    }

    @OperLog(module = "用户管理", operation = "删除")
    @DeleteMapping("/delete/{id}")
    public Result<String> deleteUser(@PathVariable Integer id) {
        boolean deleted = userService.deleteUser(id);
        if (!deleted) {
            return Result.error(404, "用户不存在");
        }
        return Result.success("删除成功");
    }

    private String getUsernameFromRequest(HttpServletRequest request) {
        String token = extractBearerToken(request);
        return jwtUtil.getUsernameFromToken(token);
    }

    private String extractBearerToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}
