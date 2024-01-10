package com.marcinski.taskprocessor.domain.task;

import com.marcinski.taskprocessor.domain.task.db.model.Task;
import com.marcinski.taskprocessor.domain.task.db.repository.TaskRepository;
import com.marcinski.taskprocessor.domain.task.exception.TaskNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
class TaskFinder {

    private final TaskRepository taskRepository;

    @Transactional
    public Task findTaskBy(String taskUuid) {
        return taskRepository.findByUuid(UUID.fromString(taskUuid))
                .orElseThrow(() -> new TaskNotFoundException(String.format("There is no task with uuid: %s", taskUuid)));
    }
}
