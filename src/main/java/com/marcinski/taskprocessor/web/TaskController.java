package com.marcinski.taskprocessor.web;

import com.marcinski.taskprocessor.domain.task.TaskService;
import com.marcinski.taskprocessor.domain.task.db.model.Task;
import com.marcinski.taskprocessor.domain.task.db.model.TaskResult;
import com.marcinski.taskprocessor.web.error.RequestValidator;
import com.marcinski.taskprocessor.web.model.CreateTaskRequest;
import com.marcinski.taskprocessor.web.model.ResultResponse;
import com.marcinski.taskprocessor.web.model.TaskResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/tasks/{taskUuid}")
    public ResponseEntity<TaskResponse> getTaskByUuid(@PathVariable String taskUuid) {
        requestValidator.validateUuid(taskUuid);
        Task task = taskService.getTask(taskUuid);
        TaskResult taskResult = task.getResult();
        TaskResponse response = new TaskResponse()
                .setUuid(task.getUuid().toString())
                .setStatus(task.getStatus().toString())
                .setResult(new ResultResponse()
                        .setPosition(taskResult.getPosition())
                        .setTypos(taskResult.getPosition()));
        return ResponseEntity.ok(response);
    }
}
