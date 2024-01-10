package com.marcinski.taskprocessor.domain.task;

import com.marcinski.taskprocessor.domain.task.db.model.Task;
import com.marcinski.taskprocessor.domain.task.db.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskCreator {

    private final TaskRepository taskRepository;

    @Transactional
    public Task createTask(String input, String pattern) {
        Task initialTask = Task.createInitialTask(input, pattern);
        return taskRepository.save(initialTask);
    }

}
