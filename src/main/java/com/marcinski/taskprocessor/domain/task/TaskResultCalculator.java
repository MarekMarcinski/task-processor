package com.marcinski.taskprocessor.domain.task;

import com.marcinski.taskprocessor.domain.task.db.model.TaskResult;
import org.springframework.stereotype.Component;

@Component
class TaskResultCalculator {

    TaskResult findBestMatch(String input, String pattern) {
        // TODO write an algorithm
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return new TaskResult(0, 0);
    }
}
