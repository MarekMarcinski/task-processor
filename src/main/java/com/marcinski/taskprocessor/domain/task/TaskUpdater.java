package com.marcinski.taskprocessor.domain.task;

import com.marcinski.taskprocessor.domain.task.db.model.Status;
import com.marcinski.taskprocessor.domain.task.db.model.Task;
import com.marcinski.taskprocessor.domain.task.db.model.TaskResult;
import com.marcinski.taskprocessor.domain.task.db.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
class TaskUpdater {

    private final TaskRepository taskRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void markTaskAsInProgress(Task taskToUpdate) {
        log.debug("Marking task {} as IN_PROGRESS", taskToUpdate);
        taskToUpdate.setStatus(Status.IN_PROGRESS);
        taskRepository.save(taskToUpdate);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void markTaskAsFinished(Task taskToUpdate, TaskResult taskResult) {
        log.debug("Marking task {} as COMPLETED", taskToUpdate);
        taskToUpdate.setResult(taskResult);
        taskToUpdate.setStatus(Status.COMPLETED);
        taskToUpdate.setProgress(100);
        taskRepository.save(taskToUpdate);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateProgress(Task taskToUpdate, int progress) {
        log.debug("Updating task progress {}", taskToUpdate);
        taskToUpdate.setProgress(progress);
        taskRepository.save(taskToUpdate);
    }
}
