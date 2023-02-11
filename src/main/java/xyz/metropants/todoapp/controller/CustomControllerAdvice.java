package xyz.metropants.todoapp.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(@NotNull Exception e) {
        Map<String, String> body = Map.of(
                "message", e.getMessage()
        );

        return ResponseEntity.badRequest().body(body);
    }

}
