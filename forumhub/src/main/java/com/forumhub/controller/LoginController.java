package com.forumhub.controller;

import com.forumhub.dto.LoginRequest;
import com.forumhub.dto.LoginResponse;
import com.forumhub.model.Usuario;
import com.forumhub.repository.UsuarioRepository;
import com.forumhub.security.TokenService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<?> autenticar(@RequestBody @Valid LoginRequest loginRequest) {
        var usuarioOpt = usuarioRepository.findByEmail(loginRequest.getEmail());

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(401).body("Credenciais inválidas");
        }

        Usuario usuario = usuarioOpt.get();

        if (!passwordEncoder.matches(loginRequest.getSenha(), usuario.getSenha())) {
            return ResponseEntity.status(401).body("Credenciais inválidas");
        }

        String token = tokenService.gerarToken(usuario.getEmail());

        return ResponseEntity.ok(new LoginResponse(token));
    }
}
