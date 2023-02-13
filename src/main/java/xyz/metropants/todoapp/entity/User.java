package xyz.metropants.todoapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import xyz.metropants.todoapp.enums.Role;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ElementCollection
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(nullable = false)
    private Set<Role> roles = EnumSet.of(Role.USER);

    @OneToMany(mappedBy = "creator")
    private Set<Todo> todos = new HashSet<>();

    public User(@NotNull String username, @NotNull String password) {
        this.username = username;
        this.password = password;
    }

}
