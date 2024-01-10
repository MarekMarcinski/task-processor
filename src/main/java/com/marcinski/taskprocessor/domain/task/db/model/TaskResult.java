package com.marcinski.taskprocessor.domain.task.db.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class TaskResult {
    private int position;
    private int typos;
}