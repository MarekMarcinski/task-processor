package com.marcinski.taskprocessor.domain.task;

import com.marcinski.taskprocessor.domain.task.db.model.Task;
import com.marcinski.taskprocessor.domain.task.db.repository.TaskRepository;
import com.marcinski.taskprocessor.domain.task.exception.TaskNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TaskFinderTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskFinder taskFinder;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindTaskBy_ExistingTask() {
        String existingTaskUuid = "b8bf0f8e-2a27-4c3a-a674-9f549055b90e";
        Task existingTask = new Task();
        when(taskRepository.findByUuid(UUID.fromString(existingTaskUuid))).thenReturn(Optional.of(existingTask));

        Task resultTask = taskFinder.findTaskBy(existingTaskUuid);

        verify(taskRepository, times(1)).findByUuid(UUID.fromString(existingTaskUuid));
        assertEquals(existingTask, resultTask);
    }

    @Test
    public void testFindTaskBy_NonExistingTask() {
        String nonExistingTaskUuid = UUID.randomUUID().toString();
        when(taskRepository.findByUuid(UUID.fromString(nonExistingTaskUuid))).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskFinder.findTaskBy(nonExistingTaskUuid));

        verify(taskRepository, times(1)).findByUuid(UUID.fromString(nonExistingTaskUuid));
    }
}