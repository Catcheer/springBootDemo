package com.example.springbootdemo.impl;

import com.example.springbootdemo.dto.LoginResponseDTO;
import com.example.springbootdemo.dto.LoginUserDTO;
import com.example.springbootdemo.dto.UpdateUserDTO;
import com.example.springbootdemo.dto.UserCreateDTO;
import com.example.springbootdemo.mapper.UserServiceMapper;
import com.example.springbootdemo.model.Userbase;
import com.example.springbootdemo.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
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
    void updateDtoShouldBindNicknameAlias() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        UpdateUserDTO dto = objectMapper.readValue(
                "{\"nickname\":\"www\",\"phone\":\"15093117985\",\"email\":\"1072491556@qq.com\"}",
                UpdateUserDTO.class
        );

        assertEquals("www", dto.getNickName());
        assertEquals("15093117985", dto.getPhone());
        assertEquals("1072491556@qq.com", dto.getEmail());
    }

    @Test
    void createUserShouldAllowMissingPasswordAndReturnCreateTime() {
        UserCreateDTO createUserDTO = new UserCreateDTO();
        createUserDTO.setUsername("new-user");
        createUserDTO.setEmail("new@example.com");
        createUserDTO.setPhone("13800000000");
        createUserDTO.setNickName("new-user");

        when(userServiceMapper.findByUsername("new-user")).thenReturn(null);

        Userbase createdUser = new Userbase();
        createdUser.setId(100);
        createdUser.setName("new-user");
        createdUser.setEmail("new@example.com");
        createdUser.setPhone("13800000000");
        createdUser.setNickName("new-user");
        createdUser.setCreateTime("2024-01-01 10:00:00");
        when(userServiceMapper.findById(100)).thenReturn(createdUser);
        when(userServiceMapper.findRolesByUserId(100)).thenReturn(List.of());

        org.mockito.Mockito.doAnswer(invocation -> {
            Userbase user = invocation.getArgument(0);
            user.setId(100);
            return null;
        }).when(userServiceMapper).insertUser(any(Userbase.class));

        LoginUserDTO result = userService.createUser(createUserDTO);

        assertNotNull(result);
        assertEquals("new-user", result.getUsername());
        assertEquals("2024-01-01 10:00:00", result.getCreateTime());
    }

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

        LoginResponseDTO result = userService.login("admin", "123456", "127.0.0.1");

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
