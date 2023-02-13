package xyz.metropants.todoapp.payload.request;

import xyz.metropants.todoapp.enums.TodoStatus;

public record TodoRequest(String todo, TodoStatus status) {}
