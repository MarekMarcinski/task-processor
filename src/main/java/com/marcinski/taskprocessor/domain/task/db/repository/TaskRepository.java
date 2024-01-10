package com.marcinski.taskprocessor.domain.task.db.repository;

import com.marcinski.taskprocessor.domain.task.db.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByUuid(UUID uuid);
}
