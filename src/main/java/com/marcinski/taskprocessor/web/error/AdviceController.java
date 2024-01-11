package com.marcinski.taskprocessor.web.error;

import com.marcinski.taskprocessor.domain.task.exception.TaskNotFoundException;
import com.marcinski.taskprocessor.web.model.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class AdviceController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WrongInputException.class)
    public ApiError handleWrongInputException(WrongInputException exception) {
        String message = exception.getMessage();
        List<String> errors = exception.getErrors();
        log.warn(message);
        errors.forEach(log::warn);
        return new ApiError()
                .setMessage(message)
                .setErrors(errors);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WrongUuidException.class)
    public ApiError handleWrongUuidException(WrongUuidException exception) {
        String message = exception.getMessage();
        log.warn(message);
        return new ApiError()
                .setMessage(message);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TaskNotFoundException.class)
    public ApiError handleTaskNotFoundException(TaskNotFoundException exception) {
        String message = exception.getMessage();
        log.warn(message);
        return new ApiError()
                .setMessage(message);
    }

//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(Exception.class)
//    public String globalHandler(Exception exception) {
//        String errorMessage = exception.getMessage();
//        log.error(errorMessage);
//        return errorMessage;
//    }
}
