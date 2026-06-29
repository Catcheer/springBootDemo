package com.example.springbootdemo.impl;

import com.example.springbootdemo.dto.LoginResponseDTO;
import com.example.springbootdemo.mapper.UserServiceMapper;
import com.example.springbootdemo.model.Userbase;
import com.example.springbootdemo.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserServiceMapper userServiceMapper;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void loginShouldReturnUserRolesAndPermissions() {
        Userbase user = new Userbase();
        user.setId(1);
        user.setName("admin");
        user.setPassword("123456");

        when(userServiceMapper.findByUsername("admin")).thenReturn(user);
        when(jwtUtil.generateAccessToken("admin")).thenReturn("access-token");
        when(jwtUtil.generateRefreshToken("admin")).thenReturn("refresh-token");
        when(userServiceMapper.findRoleCodesByUserId(1)).thenReturn(List.of("ADMIN"));
        when(userServiceMapper.findPermissionCodesByUserId(1)).thenReturn(List.of(
                "student:list",
                "student:add",
                "student:update",
                "student:delete"
        ));

        LoginResponseDTO result = userService.login("admin", "123456");

        assertNotNull(result);
        assertEquals("admin", result.getUser().getUsername());
        assertEquals(List.of("ADMIN"), result.getRoles());
        assertEquals(List.of(
                "student:list",
                "student:add",
                "student:update",
                "student:delete"
        ), result.getPermissions());
    }
}
