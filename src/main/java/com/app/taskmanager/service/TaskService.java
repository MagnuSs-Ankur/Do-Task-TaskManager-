package com.app.taskmanager.service;

import com.app.taskmanager.exception.ResourceNotFoundException;
import com.app.taskmanager.model.Task;
import com.app.taskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {

    private final TaskRepository taskRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public Task createTask(Task task) {
        log.info("Creating task {}", task.getTitle());
        Task saved = taskRepository.save(task);
        messagingTemplate.convertAndSend("/topic/tasks", saved);
        return saved;
    }

    public Task updateTask(String id, Task updatedTask) {
        Task existing = getTask(id);
        existing.setTitle(updatedTask.getTitle());
        existing.setDescription(updatedTask.getDescription());
        existing.setAssigneeId(updatedTask.getAssigneeId());
        existing.setStatus(updatedTask.getStatus());
        Task saved = taskRepository.save(existing);
        messagingTemplate.convertAndSend("/topic/tasks", saved);
        log.info("Updated task id: {}", id);
        return saved;
    }

    public Task getTask(String id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + id));
    }

    public List<Task> getTasksByProject(String projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    public void deleteTask(String id) {
        Task t = getTask(id);
        taskRepository.delete(t);
        messagingTemplate.convertAndSend("/topic/tasks", "deleted:" + id);
        log.info("Deleted task id: {}", id);
    }
}
