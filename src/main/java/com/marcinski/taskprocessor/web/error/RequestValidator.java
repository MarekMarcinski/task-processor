package com.marcinski.taskprocessor.web.error;

import com.marcinski.taskprocessor.web.model.CreateTaskRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class RequestValidator {

    private static final String ERROR_TEMPLATE = "There is no %s property in the request";

    public void validateInput(CreateTaskRequest createTaskRequest) {
        String input = createTaskRequest.getInput();
        String pattern = createTaskRequest.getPattern();
        List<String> errors = new ArrayList<>();
        if (StringUtils.isBlank(input)) {
            errors.add(String.format(ERROR_TEMPLATE, "input"));
        }
        if (StringUtils.isBlank(pattern)) {
            errors.add(String.format(ERROR_TEMPLATE, "pattern"));
        }
        if (!errors.isEmpty()) {
            throw new WrongInputException("There was a problem with provided CreateTaskRequest", errors);
        }
    }

    public void validateUuid(String taskUuid) {
        try {
            UUID.fromString(taskUuid);
        } catch (Exception exception) {
            throw new WrongUuidException("Given string is no in UUID format");
        }
    }
}
