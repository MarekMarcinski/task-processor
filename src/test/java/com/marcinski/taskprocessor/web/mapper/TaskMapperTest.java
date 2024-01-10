package com.marcinski.taskprocessor.web.mapper;

import com.marcinski.taskprocessor.domain.task.db.model.Status;
import com.marcinski.taskprocessor.domain.task.db.model.Task;
import com.marcinski.taskprocessor.domain.task.db.model.TaskResult;
import com.marcinski.taskprocessor.web.model.TaskResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = {
                TaskMapperImpl.class,
        })
class TaskMapperTest {

    @Autowired
    private TaskMapper taskMapper;

    @Test
    void testMapTaskToResponse() {
        int position = 10;
        int typos = 2;
        TaskResult result = new TaskResult(position, typos);
        Task toMap = Task.createInitialTask("ABCD", "ABC")
                .setResult(result);

        TaskResponse response = taskMapper.toResponse(toMap);
        assertNotNull(response.getUuid());
        assertEquals(Status.CREATED.toString(), response.getStatus());
        assertEquals(position, response.getResult().getPosition());
        assertEquals(typos, response.getResult().getTypos());
    }
}