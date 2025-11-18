package com.app.taskmanager.dto;

import lombok.Data;

@Data
public class AuthResponseDto {
    private String token;
    private UserResponseDto user;
}
