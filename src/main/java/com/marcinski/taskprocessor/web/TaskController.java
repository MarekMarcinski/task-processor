package com.marcinski.taskprocessor.web;

import com.marcinski.taskprocessor.domain.task.TaskService;
import com.marcinski.taskprocessor.domain.task.db.model.Task;
import com.marcinski.taskprocessor.web.error.RequestValidator;
import com.marcinski.taskprocessor.web.mapper.TaskMapper;
import com.marcinski.taskprocessor.web.model.CreateTaskRequest;
import com.marcinski.taskprocessor.web.model.TaskResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskMapper taskMapper;
    private final TaskService taskService;
    private final RequestValidator requestValidator;

    @PostMapping("/tasks")
    public ResponseEntity<String> createTask(@RequestBody CreateTaskRequest createTaskRequest) {
        requestValidator.validateInput(createTaskRequest);
        String input = createTaskRequest.getInput();
        String pattern = createTaskRequest.getPattern();
        log.info("Creating new task for input {} and pattern {}", input, pattern);
        String uuid = taskService.createTaskAndProcess(input, pattern);
        log.info("Created task with uuid {}", uuid);
        return ResponseEntity.ok(uuid);
    }

    @GetMapping("/tasks/{taskUuid}")
    public ResponseEntity<TaskResponse> getTaskByUuid(@PathVariable String taskUuid) {
        requestValidator.validateUuid(taskUuid);
        log.info("Searching task for task uuid {} request", taskUuid);
        Task task = taskService.getTask(taskUuid);
        log.info("Found a task with uuid {}", task);
        log.debug("Start mapping the Task object into TaskResponse object");
        TaskResponse response = taskMapper.toResponse(task);
        log.debug("Mapped object: {}", response);

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/tasks")
    public ResponseEntity<Page<TaskResponse>> listTasks(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                        @RequestParam(value = "size", required = false, defaultValue = "30") int size) {
        log.info("Searching tasks page with page size: {}, page number: {}", size, page);
        Page<Task> tasks = taskService.getTaskPage(page, size);

        log.debug("Start mapping the Tasks object into TaskResponses object");
        Page<TaskResponse> mappedTasks = tasks.map(taskMapper::toResponse);
        log.debug("Mapped object: {}", mappedTasks);

        return ResponseEntity.ok(mappedTasks);
    }

}
