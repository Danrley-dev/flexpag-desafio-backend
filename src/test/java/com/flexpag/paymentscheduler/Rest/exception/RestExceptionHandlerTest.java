package com.flexpag.paymentscheduler.Rest.exception;

import com.flexpag.paymentscheduler.Services.exception.DataIntegratyViolationException;
import com.flexpag.paymentscheduler.Services.exception.EmptyException;
import com.flexpag.paymentscheduler.Services.exception.ObjectNotFoundException;
import com.flexpag.paymentscheduler.Services.exception.StatusPaymentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class RestExceptionHandlerTest {

    @InjectMocks
    private RestExceptionHandler restExceptionHandler;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void objectNotFound() {
        ResponseEntity<StandardError> response = restExceptionHandler.objectNotFound(
                new ObjectNotFoundException("object not found"),
                new MockHttpServletRequest()
                );
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals("object not found", response.getBody().getError());
        assertEquals(404, response.getBody().getStatus());
    }

    @Test
    void dataIntegrityViolationException() {
        ResponseEntity<StandardError> response = restExceptionHandler.dataIntegrityViolationException(
                new DataIntegratyViolationException("invalid date"),
                new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals("invalid date", response.getBody().getError());
        assertEquals(400, response.getBody().getStatus());
    }

    @Test
    void statusPaymentException() {
        ResponseEntity<StandardError> response = restExceptionHandler.statusPaymentException(
                new StatusPaymentException("unable to delete or edit an added payment"),
                new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals("unable to delete or edit an added payment", response.getBody().getError());
        assertEquals(400, response.getBody().getStatus());
    }

    @Test
    void emptyException() {
        ResponseEntity<StandardError> response = restExceptionHandler.emptyException(
                new EmptyException("invalid, some field is empty"),
                new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals("invalid, some field is empty", response.getBody().getError());
        assertEquals(400, response.getBody().getStatus());
    }
}