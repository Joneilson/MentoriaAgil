package com.mentoria.agil.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mentoria.agil.backend.dto.LoginRequestDTO;
import com.mentoria.agil.backend.dto.UserRegistrationDTO;
import com.mentoria.agil.backend.model.User;
import com.mentoria.agil.backend.service.JwtService;
import com.mentoria.agil.backend.service.UserService;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody UserRegistrationDTO dto) {
        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setRole(dto.role());

        User savedUser = userService.salvarUsuario(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO dto) {
    // 1. Busca o usuário por email
    var user = userService.buscarPorEmail(dto.email());
    
    // 2. Verifica se a senha está correta (usando o encoder)
    if (user != null && passwordEncoder.matches(dto.password(), ((User) user).getPassword())) {
        // 3. Gera e retorna o token
        String token = jwtService.generateToken((User) user);
        return ResponseEntity.ok(token);
    }
    
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("E-mail ou senha inválidos");
}
}