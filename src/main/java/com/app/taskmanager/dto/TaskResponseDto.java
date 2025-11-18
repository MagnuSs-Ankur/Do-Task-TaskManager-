package com.app.taskmanager.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class TaskResponseDto {
    private String id;
    private String title;
    private String description;
    private String projectId;
    private String assigneeId;
    private String status;
    private Instant createdAt;
}
