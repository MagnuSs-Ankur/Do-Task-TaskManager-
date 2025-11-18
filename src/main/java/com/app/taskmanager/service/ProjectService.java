package com.app.taskmanager.service;

import com.app.taskmanager.exception.ResourceNotFoundException;
import com.app.taskmanager.model.Project;
import com.app.taskmanager.model.User;
import com.app.taskmanager.repository.ProjectRepository;
import com.app.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final UserRepository userRepository;

    public Project createProject(Project project) {
        log.info("Creating project: {}", project.getName());
        return projectRepository.save(project);
    }

    public Project updateProject(String id, Project updated) {
        Project existing = getProject(id);
        existing.setName(updated.getName());
        existing.setOwnerId(updated.getOwnerId());
        log.info("Updating project id: {}", id);
        return projectRepository.save(existing);
    }

    public Project getProject(String id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id " + id));
    }

    public List<Project> getProjectsByOwner(String ownerId) {
        return projectRepository.findByOwnerId(ownerId);
    }

    public List<Project> getProjectsByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return projectRepository.findByOwnerId(user.getId());
    }

    public void deleteProject(String id) {
        Project p = getProject(id);
        projectRepository.delete(p);
        log.info("Deleted project id: {}", id);
    }
}
