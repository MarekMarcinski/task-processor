package com.marcinski.taskprocessor.domain.task.db.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    @Test
    void testCreateInitialTask() {
        String input = "ABCD";
        String pattern = "BCD";
        Task initialTask = Task.createInitialTask(input, pattern);
        assertEquals(Status.CREATED, initialTask.getStatus());
        assertEquals(0, initialTask.getProgress());
        assertEquals(input, initialTask.getInput());
        assertEquals(pattern, initialTask.getPattern());
        assertNotNull(initialTask.getUuid());
    }
}