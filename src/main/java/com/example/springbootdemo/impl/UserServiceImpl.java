package com.example.springbootdemo.impl;

import com.example.springbootdemo.common.PageResult;
import com.example.springbootdemo.dto.LoginResponseDTO;
import com.example.springbootdemo.dto.LoginUserDTO;
import com.example.springbootdemo.dto.UpdateUserDTO;
import com.example.springbootdemo.dto.UserCreateDTO;
import com.example.springbootdemo.dto.UserQueryDTO;
import com.example.springbootdemo.mapper.UserServiceMapper;
import com.example.springbootdemo.model.Userbase;
import com.example.springbootdemo.service.UserService;
import com.example.springbootdemo.utils.JwtUtil;
import com.example.springbootdemo.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Value("${file.upload-path}")
    private String uploadPath;

    private final UserServiceMapper userServiceMapper;
    private final JwtUtil jwtUtil;

    public UserServiceImpl(UserServiceMapper userServiceMapper, JwtUtil jwtUtil) {
        this.userServiceMapper = userServiceMapper;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public LoginResponseDTO login(String username, String password, String loginIp) {
        if (username == null || password == null) {
            return null;
        }

        Userbase user = userServiceMapper.findByUsername(username);
        if (user == null) {
            return null;
        }

        if (!PasswordUtil.matchesPassword(password, user.getPassword())) {
            return null;
        }

        LocalDateTime now = LocalDateTime.now();
        userServiceMapper.updateLoginInfo(username, now, loginIp);
        user.setLastLoginTime(now);
        user.setLastLoginIp(loginIp);

        LoginResponseDTO response = new LoginResponseDTO();
        response.setAccessToken(jwtUtil.generateAccessToken(user.getName()));
        response.setRefreshToken(jwtUtil.generateRefreshToken(user.getName()));

        LoginUserDTO loginUser = new LoginUserDTO();
        loginUser.setId(user.getId());
        loginUser.setUsername(user.getName());
        loginUser.setNickname(user.getNickName());
        loginUser.setAvatar(user.getAvatar());
        loginUser.setEmail(user.getEmail());
        loginUser.setPhone(user.getPhone());
        loginUser.setLastLoginTime(user.getLastLoginTime());
        loginUser.setLastLoginIp(user.getLastLoginIp());
        loginUser.setCreateTime(user.getCreateTime());
        response.setUser(loginUser);

        List<String> roles = userServiceMapper.findRoleCodesByUserId(user.getId());
        List<String> permissions = userServiceMapper.findPermissionCodesByUserId(user.getId());
        response.setRoles(roles == null ? List.of() : roles.stream().map(String::toUpperCase).toList());
        response.setPermissions(permissions == null ? List.of() : permissions);

        return response;
    }

    @Override
    public LoginUserDTO updateUser(String username, UpdateUserDTO updateUserDTO) {
        Userbase existingUser = userServiceMapper.findByUsername(username);
        if (existingUser == null) {
            return null;
        }

        String email = updateUserDTO.getEmail();
        String phone = updateUserDTO.getPhone();
        String nickName = updateUserDTO.getNickName();

        if (email == null && phone == null && nickName == null) {
            return mapToLoginUserDTO(existingUser);
        }

        userServiceMapper.updateUserInfo(username, email, phone, nickName);
        Userbase updatedUser = userServiceMapper.findByUsername(username);
        return mapToLoginUserDTO(updatedUser);
    }

    @Override
    public String uploadAvatar(String username, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("请选择上传文件");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !originalFilename.contains(".")) {
            throw new RuntimeException("文件名不合法");
        }

        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        List<String> allowTypes = Arrays.asList(".jpg", ".jpeg", ".png", ".gif", ".webp");

        if (!allowTypes.contains(suffix.toLowerCase())) {
            throw new RuntimeException("仅支持 jpg、png、gif、webp 图片");
        }

        Path avatarDir = Paths.get(uploadPath, "avatar").toAbsolutePath().normalize();

        try {
            Files.createDirectories(avatarDir);
        } catch (IOException e) {
            throw new RuntimeException("头像目录创建失败", e);
        }

        String fileName = UUID.randomUUID() + suffix;
        Path dest = avatarDir.resolve(fileName);

        try {
            file.transferTo(dest.toFile());
        } catch (IOException e) {
            throw new RuntimeException("头像上传失败", e);
        }

        String avatarPath = "avatar/" + fileName;
        userServiceMapper.updateAvatar(username, avatarPath);
        return avatarPath;
    }

    @Override
    public PageResult<LoginUserDTO> listUsers(UserQueryDTO queryDTO) {
        if (queryDTO == null) {
            queryDTO = new UserQueryDTO();
        }

        int page = queryDTO.getPage() == null || queryDTO.getPage() < 1 ? 1 : queryDTO.getPage();
        int pageSize = queryDTO.getPageSize() == null || queryDTO.getPageSize() < 1 ? 10 : queryDTO.getPageSize();

       

        int offset = (page - 1) * pageSize;
        List<Userbase> users = userServiceMapper.findUsersByCondition(queryDTO, pageSize, offset);
        long total = userServiceMapper.countUsersByCondition(queryDTO);
        List<LoginUserDTO> list = users.stream()
                .map(this::mapToLoginUserDTO)
                .toList();

        return new PageResult<>(list, page, pageSize, total);
    }

    @Override
    public LoginUserDTO getUserById(Integer id) {
        if (id == null) {
            return null;
        }
        Userbase user = userServiceMapper.findById(id);
        return user == null ? null : mapToLoginUserDTO(user);
    }

    @Override
    public LoginUserDTO createUser(UserCreateDTO createUserDTO) {
        if (createUserDTO == null || createUserDTO.getUsername() == null) {
            return null;
        }

        Userbase existed = userServiceMapper.findByUsername(createUserDTO.getUsername());
        if (existed != null) {
            return null;
        }

        Userbase user = new Userbase();
        user.setName(createUserDTO.getUsername());
        // if (createUserDTO.getPassword() != null && !createUserDTO.getPassword().isBlank()) {
        //     user.setPassword(PasswordUtil.md5(createUserDTO.getPassword()));
        // }
        user.setEmail(createUserDTO.getEmail());
        user.setPhone(createUserDTO.getPhone());
        user.setNickName(createUserDTO.getNickName());

        userServiceMapper.insertUser(user);
        return getUserById(user.getId());
    }

    @Override
    public LoginUserDTO updateUserById(Integer id, UpdateUserDTO updateUserDTO) {
        if (id == null || updateUserDTO == null) {
            return null;
        }

        Userbase existingUser = userServiceMapper.findById(id);
        if (existingUser == null) {
            return null;
        }

        String email = updateUserDTO.getEmail();
        String phone = updateUserDTO.getPhone();
        String nickName = updateUserDTO.getNickName();

        if (email == null && phone == null && nickName == null) {
            return mapToLoginUserDTO(existingUser);
        }

        userServiceMapper.updateUserByAdmin(id, email, phone, nickName);
        Userbase updatedUser = userServiceMapper.findById(id);
        return mapToLoginUserDTO(updatedUser);
    }

    @Override
    public boolean deleteUser(Integer id) {
        if (id == null) {
            return false;
        }
        return userServiceMapper.deleteUser(id) > 0;
    }

    private LoginUserDTO mapToLoginUserDTO(Userbase user) {
        LoginUserDTO loginUser = new LoginUserDTO();
        loginUser.setId(user.getId());
        loginUser.setUsername(user.getName());
        loginUser.setNickname(user.getNickName());
        loginUser.setAvatar(user.getAvatar());
        loginUser.setEmail(user.getEmail());
        loginUser.setPhone(user.getPhone());
        loginUser.setLastLoginTime(user.getLastLoginTime());
        loginUser.setLastLoginIp(user.getLastLoginIp());
        loginUser.setCreateTime(user.getCreateTime());
        return loginUser;
    }
}
