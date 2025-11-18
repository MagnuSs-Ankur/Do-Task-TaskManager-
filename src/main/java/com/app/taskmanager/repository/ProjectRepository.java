package com.app.taskmanager.repository;

import com.app.taskmanager.model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {
    // Custom query derived automatically by Spring Data MongoDB
    List<Project> findByOwnerId(String ownerId);
}
