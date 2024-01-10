package com.marcinski.taskprocessor.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcinski.taskprocessor.domain.task.TaskService;
import com.marcinski.taskprocessor.domain.task.db.model.Task;
import com.marcinski.taskprocessor.web.error.RequestValidator;
import com.marcinski.taskprocessor.web.error.WrongInputException;
import com.marcinski.taskprocessor.web.error.WrongUuidException;
import com.marcinski.taskprocessor.web.model.CreateTaskRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RequestValidator requestValidator;

    @MockBean
    private TaskService taskService;

    @Test
    public void testCreateTask() throws Exception {
        CreateTaskRequest createTaskRequest = new CreateTaskRequest()
                .setInput("ABCD")
                .setPattern("BCD");

        String mockUuid = "rAnDoMuUiD";
        when(taskService.createTaskAndProcess(any(), any())).thenReturn(mockUuid);
        mockMvc.perform(MockMvcRequestBuilders.post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(createTaskRequest)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(mockUuid));

        verify(requestValidator, times(1)).validateInput(createTaskRequest);
    }

    @Test
    public void testCreateTaskWithWrongRequest() throws Exception {
        CreateTaskRequest createTaskRequest = new CreateTaskRequest()
                .setInput("ABCD");

        String mockUuid = "rAnDoMuUiD";
        when(taskService.createTaskAndProcess(any(), any())).thenReturn(mockUuid);
        doThrow(new WrongInputException("", new ArrayList<>())).when(requestValidator).validateInput(any());
        mockMvc.perform(MockMvcRequestBuilders.post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(createTaskRequest)))
                .andExpect(status().isBadRequest());
        verify(requestValidator, times(1)).validateInput(createTaskRequest);
        verifyNoInteractions(taskService);
    }

    @Test
    public void testGetTaskByUuid_ValidUuid() throws Exception {
        String validTaskUuid = "550e8400-e29b-41d4-a716-446655440000";
        Task mockTask = new Task();
        when(taskService.getTask(validTaskUuid)).thenReturn(mockTask);

        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/{taskUuid}", validTaskUuid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.uuid").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.position").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.typos").exists());

        verify(requestValidator, times(1)).validateUuid(validTaskUuid);

        verify(taskService, times(1)).getTask(validTaskUuid);
    }

    @Test
    public void testGetTaskByUuid_InvalidUuid() throws Exception {
        String invalidTaskUuid = "invalid-uuid";
        doThrow(new WrongUuidException("Given string is not in UUID format"))
                .when(requestValidator).validateUuid(invalidTaskUuid);

        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/{taskUuid}", invalidTaskUuid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(requestValidator, times(1)).validateUuid(invalidTaskUuid);
        verifyNoInteractions(taskService);
    }

    private String asJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}