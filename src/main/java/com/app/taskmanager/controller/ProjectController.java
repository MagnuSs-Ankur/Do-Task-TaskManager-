package com.app.taskmanager.controller;

import com.app.taskmanager.dto.ProjectRequestDto;
import com.app.taskmanager.dto.ProjectResponseDto;
import com.app.taskmanager.model.Project;
import com.app.taskmanager.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectResponseDto> create(@Valid @RequestBody ProjectRequestDto req) {
        Project p = Project.builder()
                .name(req.getName())
                .ownerId(req.getOwnerId())
                .build();
        Project saved = projectService.createProject(p);
        return ResponseEntity.ok(toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponseDto> update(@PathVariable String id, @Valid @RequestBody ProjectRequestDto req) {
        Project updatedEntity = Project.builder()
                .name(req.getName())
                .ownerId(req.getOwnerId())
                .build();
        Project updated = projectService.updateProject(id, updatedEntity);
        return ResponseEntity.ok(toDto(updated));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDto> get(@PathVariable String id) {
        return ResponseEntity.ok(toDto(projectService.getProject(id)));
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<ProjectResponseDto>> byOwner(@PathVariable String ownerId) {
        List<ProjectResponseDto> list = projectService.getProjectsByOwner(ownerId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    private ProjectResponseDto toDto(Project p) {
        ProjectResponseDto dto = new ProjectResponseDto();
        dto.setId(p.getId());
        dto.setName(p.getName());
        dto.setOwnerId(p.getOwnerId());
        return dto;
    }
}
