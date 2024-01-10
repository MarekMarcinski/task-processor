package com.marcinski.taskprocessor.web.error;

import com.marcinski.taskprocessor.web.model.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AdviceController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WrongInputException.class)
    public ApiError handleWrongInputException(WrongInputException exception) {
        return new ApiError()
                .setMessage(exception.getMessage())
                .setErrors(exception.getErrors());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String globalHandler(Exception exception) {
        String errorMessage = exception.getMessage();
        return errorMessage;
    }
}
