package com.marcinski.taskprocessor.web.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ApiError {

    private String message;
    private List<String> errors = new ArrayList<>();
}
