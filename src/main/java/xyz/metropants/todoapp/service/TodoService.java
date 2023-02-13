package xyz.metropants.todoapp.service;

import org.jetbrains.annotations.NotNull;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import xyz.metropants.todoapp.entity.Todo;
import xyz.metropants.todoapp.payload.request.TodoRequest;
import xyz.metropants.todoapp.payload.response.TodoResponse;

import java.util.List;
import java.util.Optional;

public interface TodoService {

    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface TodoMapper {

        TodoMapper INSTANCE = Mappers.getMapper(TodoMapper.class);

        Todo map(@NotNull TodoRequest request);

        TodoResponse map(@NotNull Todo todo);

        @Mapping(target = "id", ignore = true)
        Todo update(@MappingTarget Todo target, @NotNull TodoRequest source);

    }

    TodoMapper MAPPER = TodoMapper.INSTANCE;

    TodoResponse save(@NotNull TodoRequest request);

    TodoResponse update(long id, @NotNull TodoRequest request);

    void delete(long id);

    Optional<TodoResponse> findById(long id);

    List<TodoResponse> findAllByUsername(@NotNull String username);

    List<TodoResponse> findAll();

}
