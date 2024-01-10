package com.marcinski.taskprocessor.domain.task;


import com.marcinski.taskprocessor.domain.task.db.model.Task;
import com.marcinski.taskprocessor.domain.task.db.model.TaskResult;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class TaskProcessor {

    private final TaskUpdater taskUpdater;
    private final TaskResultCalculator taskResultCalculator;

    @Async
    public void process(Task task) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        taskUpdater.markTaskAsInProgress(task);

        String input = task.getInput();
        String pattern = task.getPattern();

        TaskResult result = taskResultCalculator.findBestMatch(input, pattern);

        taskUpdater.markTaskAsFinished(task, result);
    }
}