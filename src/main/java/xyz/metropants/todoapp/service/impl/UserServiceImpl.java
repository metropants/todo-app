package xyz.metropants.todoapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.metropants.todoapp.payload.request.AuthenticationRequest;
import xyz.metropants.todoapp.payload.response.UserResponse;
import xyz.metropants.todoapp.repository.UserRepository;
import xyz.metropants.todoapp.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @Override
    public UserResponse save(@NotNull AuthenticationRequest request) {
        final String username = request.username();
        if (repository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username %s is already taken.".formatted(username));
        }

        if (!UserService.isUsernameValid(username)) {
            throw new IllegalArgumentException("Username must be between 3 and 32 characters and contain only letters and numbers.");
        }

        final String password = encoder.encode(request.password());
        xyz.metropants.todoapp.entity.User user = repository.save(new xyz.metropants.todoapp.entity.User(username, password));
        return MAPPER.map(user);
    }

    @Override
    public Optional<UserResponse> findByUsername(@NotNull String username) {
        return repository.findByUsername(username)
                .map(MAPPER::map);
    }

    @Override
    public List<UserResponse> findAll() {
        return repository.findAll().stream()
                .map(MAPPER::map)
                .toList();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .map(user -> {
                    final List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority(role.name()))
                            .toList();

                    return new User(user.getUsername(), user.getPassword(), authorities);
                }).orElseThrow(() -> new UsernameNotFoundException("User with username %s not found".formatted(username)));
    }

}
