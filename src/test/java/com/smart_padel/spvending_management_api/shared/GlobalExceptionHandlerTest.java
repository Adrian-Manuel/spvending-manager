package com.smart_padel.spvending_management_api.shared;

import com.smart_padel.spvending_management_api.shared.exceptions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {
    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    void handleNotResourcesFoundException_returnsNotFoundStatusAndErrorMessage() {
        NotResourcesFoundException exception = new NotResourcesFoundException("Resource not found");
        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleNotResourcesFoundException(exception);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).containsEntry("Error", "Resource not found");
    }

    @Test
    void handleAuthorizationDeniedException_returnsUnauthorizedStatusAndErrorMessage() {
        AuthorizationDeniedException exception = new AuthorizationDeniedException("Access denied");
        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleAuthorizationDeniedException(exception);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).containsEntry("Error", "Access denied");
    }

    @Test
    void handleAuthenticationException_returnsUnauthorizedStatusAndErrorMessage() {
        AuthenticationException exception = new AuthenticationException("Authentication failed") {};
        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleAuthenticationException(exception);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).containsEntry("Error", "Authentication failed");
    }

    @Test
    void handleTokenNotFoundException_returnsUnauthorizedStatusAndErrorMessage() {
        TokenNotFoundException exception = new TokenNotFoundException("Token not found");
        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleTokenNotFoundException(exception);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).containsEntry("Error", "Token not found");
    }

    @Test
    void handleEncryptionException_returnsInternalServerErrorAndErrorMessage() {
        EncryptionException exception = new EncryptionException("Encryption error", new Throwable());
        ResponseEntity<String> response = globalExceptionHandler.handleEncryptionException(exception);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isEqualTo("Error al encriptar la contraseña: Encryption error");
    }

    @Test
    void handleParamRequiredException_returnsBadRequestStatusAndErrorMessage() {
        ParamRequiredException exception = new ParamRequiredException("Parameter is required");
        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleParamRequiredException(exception);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).containsEntry("Error", "Parameter is required");
    }

    @Test
    void handleResourceAlreadyExistsException_returnsConflictStatusAndErrorMessage() {
        ResourceAlreadyExistsException exception = new ResourceAlreadyExistsException("Resource already exists");
        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleResourceAlreadyExistsException(exception);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody()).containsEntry("Error", "Resource already exists");
    }

    @Test
    void resourceNotFoundException_returnsNotFoundStatusAndErrorMessage() {
        ResourceNotFoundException exception = new ResourceNotFoundException("Resource not found");
        ResponseEntity<Map<String, String>> response = globalExceptionHandler.resourceNotFoundException(exception);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).containsEntry("Error", "Resource not found");
    }

    @Test
    void handleDataIntegrityViolationException_returnsBadRequestStatusAndDetailMessage() {
        DataIntegrityViolationException exception = new DataIntegrityViolationException("Detail: Data integrity violation");
        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleDataIntegrityViolationException(exception);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).containsEntry("Error", "Data integrity violation");
    }

    @Test
    void handleMethodArgumentNotValidException_returnsBadRequestStatusAndFieldErrors() {
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        when(exception.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(
                new FieldError("objectName", "field1", "Field1 is invalid"),
                new FieldError("objectName", "field2", "Field2 is required")
        ));
        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleMethodArgumentNotValidException(exception);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).containsEntry("field1", "Field1 is invalid");
        assertThat(response.getBody()).containsEntry("field2", "Field2 is required");
    }

    @Test
    void handleGenericException_returnsInternalServerErrorAndErrorMessage() {
        Exception exception = new Exception("Unexpected error");

        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleGenericException(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).containsEntry("Error", "An unexpected error ocurred: Unexpected error");
    }
}
