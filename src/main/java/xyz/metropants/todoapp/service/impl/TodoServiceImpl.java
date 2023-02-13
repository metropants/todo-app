package xyz.metropants.todoapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import xyz.metropants.todoapp.config.security.WebSecurityConfig;
import xyz.metropants.todoapp.entity.Todo;
import xyz.metropants.todoapp.payload.request.TodoRequest;
import xyz.metropants.todoapp.payload.response.TodoResponse;
import xyz.metropants.todoapp.repository.TodoRepository;
import xyz.metropants.todoapp.service.TodoService;
import xyz.metropants.todoapp.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository repository;
    private final UserService userService;

    private boolean checkTodo(long id) {
        Todo todo = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Todo with id %d not found!".formatted(id)));

        final String username = WebSecurityConfig.getAuthenticatedUsername();
        return todo.getCreator().getUsername().equals(username);
    }

    @Override
    public TodoResponse save(@NotNull TodoRequest request) {
        final String username = WebSecurityConfig.getAuthenticatedUsername();
        return userService.getByUsername(username).map(user -> {
            Todo todo = MAPPER.map(request);
            if (todo.getTodo().isEmpty()) {
                throw new IllegalArgumentException("Todo cannot be empty!");
            }

            todo.setCreator(user);
            return MAPPER.map(repository.save(todo));
        }).orElseThrow(() -> new UsernameNotFoundException("User with username %s not found!".formatted(username)));
    }

    @Override
    public TodoResponse update(long id, @NotNull TodoRequest request) {
        if (!checkTodo(id)) {
            throw new IllegalArgumentException("Todo with id %d not found!".formatted(id));
        }

        return repository.findById(id).map(todo -> {
            Todo update = MAPPER.update(todo, request);
            return MAPPER.map(repository.save(update));
        }).orElseThrow(() -> new IllegalArgumentException("Todo with id %d not found!".formatted(id)));
    }

    @Override
    public void delete(long id) {
        if (!checkTodo(id)) {
            throw new IllegalArgumentException("Todo with id %d not found!".formatted(id));
        }

        repository.deleteById(id);
    }

    @Override
    public Optional<TodoResponse> findById(long id) {
        if (!checkTodo(id)) {
            return Optional.empty();
        }

        return repository.findById(id)
                .map(MAPPER::map);
    }

    @Override
    public List<TodoResponse> findAllByUsername(@NotNull String username) {
        return repository.findAllByCreator_Username(username)
                .stream()
                .map(MAPPER::map)
                .toList();
    }

    @Override
    public List<TodoResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(MAPPER::map)
                .toList();
    }

}
