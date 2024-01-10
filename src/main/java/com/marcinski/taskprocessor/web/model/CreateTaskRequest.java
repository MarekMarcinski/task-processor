package com.marcinski.taskprocessor.web.model;

import lombok.Data;

@Data
public class CreateTaskRequest {
    private String input;
    private String pattern;
}
