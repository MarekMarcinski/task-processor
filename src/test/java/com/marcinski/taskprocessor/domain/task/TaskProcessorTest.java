package com.marcinski.taskprocessor.domain.task;

import com.marcinski.taskprocessor.domain.task.db.model.Task;
import com.marcinski.taskprocessor.domain.task.db.model.TaskResult;
import com.marcinski.taskprocessor.util.Waiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TaskProcessorTest {

    @Mock
    private TaskUpdater taskUpdater;

    @Mock
    private Waiter waiter;
    @Mock
    private TaskResultCalculator taskResultCalculator;

    @InjectMocks
    private TaskProcessor taskProcessor;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess() {
        String input = "ABCD";
        String pattern = "BCD";
        Task mockTask = Task.createInitialTask(input, pattern);

        TaskResult mockResult = new TaskResult();

        when(taskResultCalculator.findBestMatch(any(), any(), any())).thenReturn(mockResult);

        taskProcessor.process(mockTask);

        verify(taskUpdater, times(1)).markTaskAsInProgress(mockTask);

        verify(taskResultCalculator, times(1)).findBestMatch(any(), any(), any());

        verify(taskUpdater, times(1)).markTaskAsFinished(mockTask, mockResult);
    }
}