package com.app.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProjectRequestDto {
    @NotBlank
    private String name;

    @NotBlank
    private String ownerId;
}
