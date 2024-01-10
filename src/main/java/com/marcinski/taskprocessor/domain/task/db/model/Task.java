package com.marcinski.taskprocessor.domain.task.db.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID uuid = UUID.randomUUID();
    private String input;
    private String pattern;
    @Enumerated(EnumType.STRING)
    private Status status = Status.CREATED;
    private int progress = 0;
    private TaskResult result;
}
