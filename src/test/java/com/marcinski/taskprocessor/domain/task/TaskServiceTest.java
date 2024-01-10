package com.marcinski.taskprocessor.domain.task;

import com.marcinski.taskprocessor.domain.task.db.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Mock
    private TaskCreator taskCreator;
    @Mock
    private TaskProcessor taskProcessor;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Transactional
    public void testCreateTaskAndProcess() {
        String input = "ABCD";
        String pattern = "BCD";

        Task mockTask = Task.createInitialTask(input, pattern);
        when(taskCreator.createTask(input, pattern)).thenReturn(mockTask);

        String resultUuid = taskService.createTaskAndProcess(input, pattern);

        verify(taskCreator, times(1)).createTask(input, pattern);
        verify(taskProcessor, times(1)).process(mockTask);

        assertEquals(mockTask.getUuid().toString(), resultUuid);
    }
}