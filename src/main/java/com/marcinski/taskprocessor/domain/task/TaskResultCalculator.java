package com.marcinski.taskprocessor.domain.task;

import com.marcinski.taskprocessor.domain.task.db.model.TaskResult;
import com.marcinski.taskprocessor.util.Waiter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
class TaskResultCalculator {

    private final Waiter waiter;

    TaskResult findBestMatch(String input, String pattern, Consumer<Integer> onProgressChange) {
        int bestPosition = 0;
        int bestTypos = Integer.MAX_VALUE;

        int loopLength = input.length() - pattern.length();
        int updateProgressEvery = Math.max(10, loopLength / 100);
        for (int i = 0; i <= loopLength; i++) {
            int typos = calculateTypos(input.substring(i, i + pattern.length()), pattern);

            if (typos < bestTypos) {
                bestTypos = typos;
                bestPosition = i;
            }

            if (typos == 0) {
                break;
            }

            if (i % updateProgressEvery == 0 && i != 0) {
                onProgressChange.accept(100 * i / loopLength);
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
