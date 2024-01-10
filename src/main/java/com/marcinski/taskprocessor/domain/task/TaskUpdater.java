package com.marcinski.taskprocessor.domain.task;

import com.marcinski.taskprocessor.domain.task.db.model.Status;
import com.marcinski.taskprocessor.domain.task.db.model.Task;
import com.marcinski.taskprocessor.domain.task.db.model.TaskResult;
import com.marcinski.taskprocessor.domain.task.db.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class TaskUpdater {

    private final TaskRepository taskRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void markTaskAsInProgress(Task taskToUpdate) {
        taskToUpdate.setStatus(Status.IN_PROGRESS);
        taskRepository.save(taskToUpdate);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void markTaskAsFinished(Task taskToUpdate, TaskResult taskResult) {
        taskToUpdate.setResult(taskResult);
        taskToUpdate.setStatus(Status.COMPLETED);
        taskToUpdate.setProgress(100);
        taskRepository.save(taskToUpdate);
    }
}
