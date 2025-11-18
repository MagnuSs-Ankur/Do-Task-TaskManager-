package com.app.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TaskRequestDto {
    private String id; // optional for updates
    @NotBlank
    private String title;
    private String description;
    @NotBlank
    private String projectId;
    private String assigneeId;
    private String status;
}
