package com.app.taskmanager.controller;

import com.app.taskmanager.dto.AuthResponseDto;
import com.app.taskmanager.dto.UserRequestDto;
import com.app.taskmanager.dto.UserResponseDto;
import com.app.taskmanager.model.User;
import com.app.taskmanager.security.JwtUtil;
import com.app.taskmanager.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody UserRequestDto req) {
        User created = userService.register(req.getUsername(), req.getPassword());
        UserResponseDto resp = toUserResponseDto(created);
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody UserRequestDto req) {
        User u = userService.validateUser(req.getUsername(), req.getPassword());
        String token = jwtUtil.generateToken(u.getUsername());
        AuthResponseDto resp = new AuthResponseDto();
        resp.setToken(token);
        resp.setUser(toUserResponseDto(u));
        return ResponseEntity.ok(resp);
    }

    private UserResponseDto toUserResponseDto(User u) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(u.getId());
        dto.setUsername(u.getUsername());
        return dto;
    }
}
