package com.marcinski.taskprocessor.domain.task;

import com.marcinski.taskprocessor.domain.task.db.model.TaskResult;
import com.marcinski.taskprocessor.util.Waiter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
class TaskResultCalculator {

    private final Waiter waiter;

    TaskResult findBestMatch(String input, String pattern) {
        int bestPosition = 0;
        int bestTypos = Integer.MAX_VALUE;

        for (int i = 0; i <= input.length() - pattern.length(); i++) {
            int typos = calculateTypos(input.substring(i, i + pattern.length()), pattern);

            if (typos < bestTypos) {
                bestTypos = typos;
                bestPosition = i;
            }
            waiter.simulateDelay();
        }

        return new TaskResult(bestPosition, bestTypos);
    }

    private int calculateTypos(String str1, String str2) {
        return (int) IntStream.range(0, str1.length())
                .filter(i -> str1.charAt(i) != str2.charAt(i))
                .count();
    }
}
