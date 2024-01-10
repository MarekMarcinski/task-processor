package com.marcinski.taskprocessor.domain.task;

import com.marcinski.taskprocessor.domain.task.db.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskFinder taskFinder;
    private final TaskCreator taskCreator;
    private final TaskProcessor taskProcessor;

    public String createTaskAndProcess(String input, String pattern) {
        Task saved = taskCreator.createTask(input, pattern);

        taskProcessor.process(saved);
        return saved.getUuid().toString();
    }

    public Task getTask(String taskUuid) {
        return taskFinder.findTaskBy(taskUuid);
    }

    public Page<Task> getTaskPage(int page, int size) {
        return taskFinder.getTaskPage(page, size);
    }
}
