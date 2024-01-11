package com.marcinski.taskprocessor.domain.task;

import com.marcinski.taskprocessor.domain.task.db.model.Status;
import com.marcinski.taskprocessor.domain.task.db.model.Task;
import com.marcinski.taskprocessor.domain.task.db.model.TaskResult;
import com.marcinski.taskprocessor.domain.task.db.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TaskUpdaterTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskUpdater taskUpdater;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMarkTaskAsInProgress() {
        String input = "ABCD";
        String pattern = "BCD";
        Task mockTask = Task.createInitialTask(input, pattern);

        taskUpdater.markTaskAsInProgress(mockTask);

        assertEquals(Status.IN_PROGRESS, mockTask.getStatus());
        verify(taskRepository, times(1)).save(mockTask);
    }

    @Test
    public void testMarkTaskAsFinished() {
        String input = "ABCD";
        String pattern = "BCD";
        Task mockTask = Task.createInitialTask(input, pattern);

        TaskResult mockResult = new TaskResult();

        taskUpdater.markTaskAsFinished(mockTask, mockResult);

        assertEquals(Status.COMPLETED, mockTask.getStatus());
        assertEquals(100, mockTask.getProgress());
        assertEquals(mockResult, mockTask.getResult());
        verify(taskRepository, times(1)).save(mockTask);
    }

    @Test
    public void testUpdateProgress() {
        String input = "ABCD";
        String pattern = "BCD";
        Task mockTask = Task.createInitialTask(input, pattern);

        taskUpdater.updateProgress(mockTask, 80);

        assertEquals(80, mockTask.getProgress());
        verify(taskRepository, times(1)).save(mockTask);
    }
}