package xyz.metropants.todoapp.controller.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.metropants.todoapp.config.security.JWTConfig;
import xyz.metropants.todoapp.payload.request.AuthenticationRequest;
import xyz.metropants.todoapp.payload.response.JWTResponse;
import xyz.metropants.todoapp.payload.response.UserResponse;
import xyz.metropants.todoapp.service.UserService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager manager;
    private final UserService service;
    private final JWTConfig config;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody AuthenticationRequest request) {
        UserResponse response = service.save(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JWTResponse> login(@RequestBody AuthenticationRequest request) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.username(), request.password());
        Authentication authentication = manager.authenticate(token);

        User user = (User) authentication.getPrincipal();
        JWTResponse response = config.generate(user);
        return ResponseEntity.ok(response);
    }

}
