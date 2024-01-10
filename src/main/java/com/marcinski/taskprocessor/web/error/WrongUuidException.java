package com.marcinski.taskprocessor.web.error;

import lombok.Getter;

@Getter
public class WrongUuidException extends RuntimeException {

    public WrongUuidException(String message) {
        super(message);
    }
}
