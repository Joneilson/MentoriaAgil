package com.mentoria.agil.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mentoria.agil.backend.dto.UserDTO;
import com.mentoria.agil.backend.model.User;
import com.mentoria.agil.backend.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registrarUsuario(@RequestBody UserDTO userDTO) {
        try {
            User novoUsuario = userService.salvarUsuario(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário cadastrado com sucesso. ID: " + novoUsuario.getId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }
}