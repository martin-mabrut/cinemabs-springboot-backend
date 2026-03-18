package com.cinefamille.api.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.cinefamille.api.exception.ResourceNotFoundException;
import com.cinefamille.api.exception.ConflictException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        // TODO 1 : créer une Map<String, Object> nommée "errors"
        Map<String, Object> error = new HashMap<>();
        // TODO 2 : y mettre "status" -> 400
        error.put("status", 400);
        // TODO 3 : y mettre "timestamp" -> LocalDateTime.now()
        error.put("timestamp", LocalDateTime.now());
        // TODO 4 : récupérer le premier message d'erreur depuis ex.getBindingResult().getFieldErrors()
        //          et le mettre dans "message"
        error.put("message", ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
        // TODO 5 : retourner ResponseEntity.status(400).body(errors)
        return ResponseEntity.status(400).body(error);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFoundException(ResourceNotFoundException ex) {
        // TODO 1 : créer une Map<String, Object> nommée "error"
        Map<String, Object> error = new HashMap<>();
        // TODO 2 : y mettre "status" -> 404
        error.put("status", 404);
        // TODO 3 : y mettre "timestamp" -> LocalDateTime.now()
        error.put("timestamp", LocalDateTime.now());
        // TODO 4 : y mettre "message" -> ex.getMessage()
        error.put("message", ex.getMessage());
        // TODO 5 : retourner ResponseEntity.status(404).body(error)
        return ResponseEntity.status(404).body(error);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Map<String, Object>> handleConflictException(ConflictException ex) {
        // TODO 1 : créer une Map<String, Object> nommée "error"
        Map<String, Object> error = new HashMap<>();
        // TODO 2 : y mettre "status" -> 409
        error.put("status", 409);
        // TODO 3 : y mettre "timestamp" -> LocalDateTime.now()
        error.put("timestamp", LocalDateTime.now());
        // TODO 4 : y mettre "message" -> ex.getMessage()
        error.put("message", ex.getMessage());
        // TODO 5 : retourner ResponseEntity.status(409).body(error)
        return ResponseEntity.status(409).body(error);
    }

}
