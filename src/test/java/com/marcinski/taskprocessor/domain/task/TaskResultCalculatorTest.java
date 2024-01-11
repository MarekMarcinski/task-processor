package com.marcinski.taskprocessor.domain.task;

import com.marcinski.taskprocessor.domain.task.db.model.Task;
import com.marcinski.taskprocessor.domain.task.db.model.TaskResult;
import com.marcinski.taskprocessor.util.Waiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TaskResultCalculatorTest {
    @Mock
    private TaskUpdater taskUpdater;
    @Mock
    private Waiter mockWaiter;

    @InjectMocks
    private TaskResultCalculator taskResultCalculator;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("testData")
    public void testFindBestMatch(String input, String pattern, int position, int typos, int updaterCalled) {
        doNothing().when(mockWaiter).simulateDelay();

        TaskResult result = taskResultCalculator.findBestMatch(input, pattern, (progress) -> taskUpdater.updateProgress(new Task(), progress));

        assertEquals(position, result.getPosition());
        assertEquals(typos, result.getTypos());
        verify(taskUpdater, times(updaterCalled)).updateProgress(any(), anyInt());
    }


    private static Stream<Arguments> testData() {
        return Stream.of(
                Arguments.of("", "", 0, 0, 0),
                Arguments.of("ABCD", "BCD", 1, 0, 0),
                Arguments.of("ABCD", "BWD", 1, 1, 0),
                Arguments.of("ABCDEFG", "CFG", 4, 1, 0),
                Arguments.of("ABCABC", "ABC", 0, 0, 0),
                Arguments.of("ABCDEFG", "TDD", 1, 2, 0),
                Arguments.of("ABCDEFGHIJKLMNOPRSTUWXYZ", "XYZ", 21, 0, 2)
        );
    }
}