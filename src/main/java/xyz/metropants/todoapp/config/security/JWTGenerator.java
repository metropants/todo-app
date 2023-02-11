package xyz.metropants.todoapp.config.security;

import com.auth0.jwt.algorithms.Algorithm;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.UserDetails;
import xyz.metropants.todoapp.payload.response.JWTResponse;

@FunctionalInterface
public interface JWTGenerator {

    JWTResponse generate(@NotNull Algorithm algorithm, @NotNull UserDetails details);

}
