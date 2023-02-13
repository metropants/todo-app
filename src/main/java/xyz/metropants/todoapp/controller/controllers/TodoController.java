package xyz.metropants.todoapp.controller.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import xyz.metropants.todoapp.payload.request.TodoRequest;
import xyz.metropants.todoapp.payload.response.TodoResponse;
import xyz.metropants.todoapp.service.TodoService;

import java.util.List;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService service;

    @PostMapping("/")
    public ResponseEntity<TodoResponse> save(@RequestBody TodoRequest request) {
        TodoResponse response = service.save(request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TodoResponse> update(@PathVariable long id, @RequestBody TodoRequest request) {
        TodoResponse response = service.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/")
    public ResponseEntity<List<TodoResponse>> findAll(@AuthenticationPrincipal String username) {
        List<TodoResponse> response = service.findAllByUsername(username);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponse> findById(@PathVariable long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
