package com.marcinski.taskprocessor.web.error;

import com.marcinski.taskprocessor.web.model.CreateTaskRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RequestValidatorTest {

    private final RequestValidator requestValidator = new RequestValidator();


    @Test
    public void testValidateInput_ValidRequest() {
        var createTaskRequest = new CreateTaskRequest()
                .setInput("ABCD")
                .setPattern("BCD");
        requestValidator.validateInput(createTaskRequest);
    }

    @Test
    public void testValidateInput_BlankInput() {
        var createTaskRequest = new CreateTaskRequest()
                .setPattern("BCD");

        WrongInputException exception = assertThrows(WrongInputException.class,
                () -> requestValidator.validateInput(createTaskRequest));

        assertEquals(1, exception.getErrors().size());
    }

    @Test
    public void testValidateInput_BlankInputAndPattern() {
        var createTaskRequest = new CreateTaskRequest();

        WrongInputException exception = assertThrows(WrongInputException.class,
                () -> requestValidator.validateInput(createTaskRequest));

        assertEquals(2, exception.getErrors().size());
    }

    @Test
    public void testValidateUuid_ValidUuid() {
        String validUuid = "550e8400-e29b-41d4-a716-446655440000";

        assertDoesNotThrow(() -> requestValidator.validateUuid(validUuid));
    }

    @Test
    public void testValidateUuid_InvalidUuid() {
        String invalidUuid = "invalid-uuid";

        assertThrows(WrongUuidException.class,
                () -> requestValidator.validateUuid(invalidUuid));
    }

}