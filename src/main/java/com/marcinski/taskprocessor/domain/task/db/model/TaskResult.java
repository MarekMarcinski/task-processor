package com.marcinski.taskprocessor.domain.task.db.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class TaskResult {
    private int position;
    private int typos;
}