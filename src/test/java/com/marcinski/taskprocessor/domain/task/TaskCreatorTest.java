package com.marcinski.taskprocessor.domain.task;

import com.marcinski.taskprocessor.domain.task.db.model.Task;
import com.marcinski.taskprocessor.domain.task.db.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

class TaskCreatorTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskCreator taskCreator;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTask() {
        String input = "ABCD";
        String pattern = "BCD";

        Task mockTask = Task.createInitialTask(input, pattern);
        when(taskRepository.save(any())).thenReturn(mockTask);

        Task resultTask = taskCreator.createTask(input, pattern);

        verify(taskRepository, times(1)).save(argThat(task -> task.getInput().equals(input) && task.getPattern().equals(pattern)));

        assertEquals(mockTask, resultTask);
    }

}