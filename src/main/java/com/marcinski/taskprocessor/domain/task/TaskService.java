package com.marcinski.taskprocessor.domain.task;

import com.marcinski.taskprocessor.domain.task.db.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskCreator taskCreator;
    private final TaskProcessor taskProcessor;

    public String createTaskAndProcess(String input, String pattern) {
        Task saved = taskCreator.createTask(input, pattern);

        taskProcessor.process(saved);
        return saved.getUuid().toString();
    }
}
