package com.marcinski.taskprocessor.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcinski.taskprocessor.domain.task.TaskService;
import com.marcinski.taskprocessor.web.error.RequestValidator;
import com.marcinski.taskprocessor.web.error.WrongInputException;
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
                .andExpect(MockMvcResultMatchers.status().isOk())
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
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        verify(requestValidator, times(1)).validateInput(createTaskRequest);
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