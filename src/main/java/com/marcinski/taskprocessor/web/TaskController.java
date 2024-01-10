package com.marcinski.taskprocessor.web;

import com.marcinski.taskprocessor.domain.task.TaskService;
import com.marcinski.taskprocessor.web.error.RequestValidator;
import com.marcinski.taskprocessor.web.model.CreateTaskRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final RequestValidator requestValidator;

    @PostMapping("/tasks")
    public ResponseEntity<String> createTask(@RequestBody CreateTaskRequest createTaskRequest) {
        requestValidator.validateInput(createTaskRequest);
        String uuid = taskService.createTaskAndProcess(createTaskRequest.getInput(), createTaskRequest.getPattern());
        return ResponseEntity.ok(uuid);
    }
}
