package xyz.metropants.todoapp.service;

import org.jetbrains.annotations.NotNull;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.userdetails.UserDetailsService;
import xyz.metropants.todoapp.entity.User;
import xyz.metropants.todoapp.payload.request.AuthenticationRequest;
import xyz.metropants.todoapp.payload.response.UserResponse;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    String USERNAME_REGEX = "[a-zA-Z0-9]{3,32}$";

    static boolean isUsernameValid(@NotNull String username) {
        return username.matches(USERNAME_REGEX);
    }

    @Mapper(componentModel = "spring")
    interface UserMapper {

        UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

        UserResponse map(@NotNull User user);

    }

    UserMapper MAPPER = UserMapper.INSTANCE;

    UserResponse save(@NotNull AuthenticationRequest request);

    Optional<User> getByUsername(@NotNull String username);

    Optional<UserResponse> findByUsername(@NotNull String username);

    List<UserResponse> findAll();

}
