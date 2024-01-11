package com.marcinski.taskprocessor.domain.task;

import com.marcinski.taskprocessor.domain.task.db.model.Task;
import com.marcinski.taskprocessor.domain.task.db.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskCreator {

    private final TaskRepository taskRepository;

    @Transactional
    public Task createTask(String input, String pattern) {
        Task initialTask = Task.createInitialTask(input, pattern);
        log.debug("Persisting new task {} into db", initialTask);
        return taskRepository.save(initialTask);
    }

}
