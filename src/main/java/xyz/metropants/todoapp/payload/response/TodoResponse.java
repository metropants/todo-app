package xyz.metropants.todoapp.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.metropants.todoapp.enums.TodoStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoResponse {

    private long id;

    private String todo;

    private TodoStatus status;

}
