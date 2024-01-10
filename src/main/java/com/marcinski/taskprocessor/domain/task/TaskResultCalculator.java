package com.marcinski.taskprocessor.domain.task;

import com.marcinski.taskprocessor.domain.task.db.model.TaskResult;
import com.marcinski.taskprocessor.util.Waiter;
import org.springframework.stereotype.Component;

@Component
class TaskResultCalculator {

    TaskResult findBestMatch(String input, String pattern) {
        // TODO write an algorithm
        Waiter.simulateDelay();

        return new TaskResult(0, 0);
    }
}
