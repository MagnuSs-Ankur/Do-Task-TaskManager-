package com.app.taskmanager.controller;

import com.app.taskmanager.dto.TaskRequestDto;
import com.app.taskmanager.dto.TaskResponseDto;
import com.app.taskmanager.model.Task;
import com.app.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponseDto> create(@Valid @RequestBody TaskRequestDto req) {
        Task task = Task.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .projectId(req.getProjectId())
                .assigneeId(req.getAssigneeId())
                .status(req.getStatus() == null ? "TODO" : req.getStatus())
                .build();
        Task saved = taskService.createTask(task);
        return ResponseEntity.ok(toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDto> update(@PathVariable String id, @Valid @RequestBody TaskRequestDto req) {
        Task updatedEntity = Task.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .projectId(req.getProjectId())
                .assigneeId(req.getAssigneeId())
                .status(req.getStatus())
                .build();
        Task updated = taskService.updateTask(id, updatedEntity);
        return ResponseEntity.ok(toDto(updated));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> get(@PathVariable String id) {
        return ResponseEntity.ok(toDto(taskService.getTask(id)));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<TaskResponseDto>> byProject(@PathVariable String projectId) {
        List<TaskResponseDto> list = taskService.getTasksByProject(projectId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    private TaskResponseDto toDto(Task t) {
        TaskResponseDto dto = new TaskResponseDto();
        dto.setId(t.getId());
        dto.setTitle(t.getTitle());
        dto.setDescription(t.getDescription());
        dto.setProjectId(t.getProjectId());
        dto.setAssigneeId(t.getAssigneeId());
        dto.setStatus(t.getStatus());
        dto.setCreatedAt(t.getCreatedAt());
        return dto;
    }
}
