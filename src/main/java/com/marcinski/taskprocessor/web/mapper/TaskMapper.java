package com.marcinski.taskprocessor.web.mapper;

import com.marcinski.taskprocessor.domain.task.db.model.Task;
import com.marcinski.taskprocessor.web.model.TaskResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskResponse toResponse(Task task);
}
