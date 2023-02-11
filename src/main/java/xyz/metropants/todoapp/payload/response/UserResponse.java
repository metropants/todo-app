package xyz.metropants.todoapp.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.metropants.todoapp.enums.Role;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private long id;

    private String username;

    private Set<Role> roles;

}
