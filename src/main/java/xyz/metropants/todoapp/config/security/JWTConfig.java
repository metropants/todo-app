package xyz.metropants.todoapp.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import xyz.metropants.todoapp.payload.response.JWTResponse;

import java.time.Instant;
import java.util.stream.Collectors;

@Configuration
public class JWTConfig {

    @Value("${jwt.secret.key}")
    private String secret;

    @Bean
    public Algorithm algorithm() {
        return Algorithm.HMAC256(secret);
    }

    @Bean
    public JWTVerifier verifier(@NotNull Algorithm algorithm) {
        return JWT.require(algorithm)
                .build();
    }

    public JWTResponse generate(@NotNull UserDetails user) {
        JWTGenerator generator = (algorithm, details) -> {
            final String roles = details.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));
            final String token = JWT.create()
                    .withIssuedAt(Instant.now())
                    .withSubject(details.getUsername())
                    .withClaim("roles", roles)
                    .sign(algorithm);

            return JWTResponse.builder()
                    .token(token)
                    .build();
        };

        return generator.generate(algorithm(), user);
    }

    public DecodedJWT decode(@NotNull String token) throws JWTVerificationException {
        return verifier(algorithm())
                .verify(token);
    }

}
