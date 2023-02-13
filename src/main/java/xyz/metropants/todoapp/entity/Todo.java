package xyz.metropants.todoapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import xyz.metropants.todoapp.enums.TodoStatus;

@Data
@Entity
@Table(name = "todos")
@NoArgsConstructor
@AllArgsConstructor
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String todo;

    @ManyToOne
    private User creator;

    private TodoStatus status = TodoStatus.IN_PROGRESS;

    public Todo(@NotNull String todo) {
        this.todo = todo;
    }

}
