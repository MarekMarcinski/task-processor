package com.marcinski.taskprocessor.web.error;

import lombok.Getter;

import java.util.List;

@Getter
public class WrongInputException extends RuntimeException {

    private final List<String> errors;
    public WrongInputException(String message, List<String> errors) {
        super(message);
        this.errors = errors;
    }
}
