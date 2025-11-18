package com.app.taskmanager.dto;

import lombok.Data;

@Data
public class ProjectResponseDto {
    private String id;
    private String name;
    private String ownerId;
}
