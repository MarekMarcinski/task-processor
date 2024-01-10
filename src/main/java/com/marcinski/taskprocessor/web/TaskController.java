package com.marcinski.taskprocessor.web;

import com.marcinski.taskprocessor.domain.task.TaskService;
import com.marcinski.taskprocessor.domain.task.db.model.Task;
import com.marcinski.taskprocessor.web.error.RequestValidator;
import com.marcinski.taskprocessor.web.mapper.TaskMapper;
import com.marcinski.taskprocessor.web.model.CreateTaskRequest;
import com.marcinski.taskprocessor.web.model.TaskResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskMapper taskMapper;
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
        TaskResponse response = taskMapper.toResponse(task);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/tasks")
    public ResponseEntity<Page<TaskResponse>> listTasks(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                        @RequestParam(value = "size", required = false, defaultValue = "30") int size) {
        Page<Task> tasks = taskService.getTaskPage(page, size);
        Page<TaskResponse> mappedTasks = tasks.map(taskMapper::toResponse);
        return ResponseEntity.ok(mappedTasks);
    }

}
