package com.marcinski.taskprocessor.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Waiter {

    public void simulateDelay() {
        try {
            log.info("Simulate delay");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
