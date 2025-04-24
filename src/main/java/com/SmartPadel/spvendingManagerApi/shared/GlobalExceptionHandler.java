package com.SmartPadel.spvendingManagerApi.shared;
import com.SmartPadel.spvendingManagerApi.shared.Exceptions.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotResourcesFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotResourcesFoundException(NotResourcesFoundException ex){
        Map<String, String> errors=new HashMap<>();
        errors.put("Error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<Map<String, String>> handleAuthorizationDeniedException(AuthorizationDeniedException ex){
        Map<String, String> errors=new HashMap<>();
        errors.put("Error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errors);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, String>> handleAuthenticationException(AuthenticationException ex){
        Map<String, String> errors=new HashMap<>();
        errors.put("Error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errors);
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleTokenNotFoundException(TokenNotFoundException ex){
        Map<String, String> errors=new HashMap<>();
        errors.put("Error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errors);
    }

    @ExceptionHandler(EncryptionException.class)
    public ResponseEntity<String> handleEncryptionException(EncryptionException ex) {
        return ResponseEntity
                .status(500)
                .body("Error al encriptar la contraseña: " + ex.getMessage());
    }


    @ExceptionHandler(ParamRequiredException.class)
    public ResponseEntity<Map<String, String>> handleParamRequiredException(ParamRequiredException ex){
        Map<String, String> errors=new HashMap<>();
        errors.put("Error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex){
        Map<String, String> errors=new HashMap<>();
        errors.put("Error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> ResourceNotFoundException(ResourceNotFoundException ex){
        Map<String, String> errors=new HashMap<>();
        errors.put("Error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolationException(DataIntegrityViolationException ex){
        String fullMessage = ex.getMostSpecificCause().getMessage();
        String detailMessage = Arrays.stream(fullMessage.split("\n"))
                .filter(line -> line.trim().startsWith("Detail:"))
                .map(line -> line.replace("Detail:", "").trim())
                .findFirst()
                .orElse("Violación de integridad de datos.");

        Map<String, String> errors = new HashMap<>();
        errors.put("error", detailMessage);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        Map<String, String> errors=new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex){
        Map<String, String> errors=new HashMap<>();
        errors.put("Error", "An unexpected error ocurred: "+ ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errors);
    }



}
