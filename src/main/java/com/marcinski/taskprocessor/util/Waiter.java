package com.marcinski.taskprocessor.util;

public class Waiter {

    public static void simulateDelay() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
