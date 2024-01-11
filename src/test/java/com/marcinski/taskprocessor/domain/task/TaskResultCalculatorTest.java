package com.marcinski.taskprocessor.domain.task;

import com.marcinski.taskprocessor.domain.task.db.model.TaskResult;
import com.marcinski.taskprocessor.util.Waiter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

class TaskResultCalculatorTest {

    @ParameterizedTest
    @MethodSource("testData")
    public void testFindBestMatch(String input, String pattern, int position, int typos) {
        Waiter mockWaiter = mock(Waiter.class);
        doNothing().when(mockWaiter).simulateDelay();
        TaskResultCalculator taskResultCalculator = new TaskResultCalculator(mockWaiter);

        TaskResult result = taskResultCalculator.findBestMatch(input, pattern);

        assertEquals(position, result.getPosition());
        assertEquals(typos, result.getTypos());
    }


    private static Stream<Arguments> testData() {
        return Stream.of(
                Arguments.of("", "", 0, 0),
                Arguments.of("ABCD", "BCD", 1, 0),
                Arguments.of("ABCD", "BWD", 1, 1),
                Arguments.of("ABCDEFG", "CFG", 4, 1),
                Arguments.of("ABCABC", "ABC", 0, 0),
                Arguments.of("ABCDEFG", "TDD", 1, 2)
        );
    }
}