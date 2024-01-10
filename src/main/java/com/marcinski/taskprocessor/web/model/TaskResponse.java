package com.marcinski.taskprocessor.web.model;

import lombok.Data;

@Data
public class TaskResponse {

    private String uuid;
    private String status;
    private ResultResponse result;
}
