package com.marcinski.taskprocessor.domain.task.db.repository;

import com.marcinski.taskprocessor.domain.task.db.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
