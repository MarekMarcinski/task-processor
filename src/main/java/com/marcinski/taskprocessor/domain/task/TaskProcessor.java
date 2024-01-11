package com.marcinski.taskprocessor.domain.task;


import com.marcinski.taskprocessor.domain.task.db.model.Task;
import com.marcinski.taskprocessor.domain.task.db.model.TaskResult;
import com.marcinski.taskprocessor.util.Waiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
class TaskProcessor {

    private final Waiter waiter;
    private final TaskUpdater taskUpdater;
    private final TaskResultCalculator taskResultCalculator;

    @Async
    public void process(Task task) {
        log.debug("Processing of a task {} started!", task);
        long start = System.currentTimeMillis();
        waiter.simulateDelay();

        taskUpdater.markTaskAsInProgress(task);

        String input = task.getInput();
        String pattern = task.getPattern();

        TaskResult result = taskResultCalculator.findBestMatch(input, pattern,
                (progress) -> taskUpdater.updateProgress(task, progress));

        taskUpdater.markTaskAsFinished(task, result);
        long end = System.currentTimeMillis();
        log.debug("Processing of a task {} ended after {} mills!", task, end - start);
    }
}