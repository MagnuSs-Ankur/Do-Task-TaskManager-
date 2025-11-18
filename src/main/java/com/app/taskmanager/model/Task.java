package com.app.taskmanager.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document("tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    @Id
    private String id;
    private String title;
    private String description;
    private String projectId;
    private String assigneeId;
    private String status; // TODO, IN_PROGRESS, DONE
    private Instant createdAt;
}
