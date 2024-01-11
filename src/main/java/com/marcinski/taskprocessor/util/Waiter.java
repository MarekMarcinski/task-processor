package com.marcinski.taskprocessor.util;

import org.springframework.stereotype.Component;

@Component
public class Waiter {

    public void simulateDelay() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
