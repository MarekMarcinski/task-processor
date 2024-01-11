package com.marcinski.taskprocessor.domain.task;

import com.marcinski.taskprocessor.domain.task.db.model.Task;
import com.marcinski.taskprocessor.domain.task.db.repository.TaskRepository;
import com.marcinski.taskprocessor.domain.task.exception.TaskNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
class TaskFinder {

    private final TaskRepository taskRepository;

    @Transactional
    public Task findTaskBy(String taskUuid) {
        log.debug("Searching for a task with uuid {}", taskUuid);
        return taskRepository.findByUuid(UUID.fromString(taskUuid))
                .orElseThrow(() -> new TaskNotFoundException(String.format("There is no task with uuid: %s", taskUuid)));
    }

    @Transactional
    public Page<Task> getTaskPage(int page, int size) {
        log.debug("Searching for a tasks as a page with page {}, size {}", page, size);
        return taskRepository.findAll(PageRequest.of(page, size));
    }
}
