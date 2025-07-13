package com.forumhub.controller;

import com.forumhub.dto.UsuarioRequest;
import com.forumhub.dto.UsuarioResponse;
import com.forumhub.model.Usuario;
import com.forumhub.repository.UsuarioRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Listar todos os usuários
    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listar() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioResponse> resposta = usuarios.stream()
                .map(UsuarioResponse::new)
                .toList();
        return ResponseEntity.ok(resposta);
    }

    // Buscar usuário por id
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .map(usuario -> ResponseEntity.ok(new UsuarioResponse(usuario)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Criar um novo usuário
    @PostMapping
    public ResponseEntity<UsuarioResponse> cadastrar(@RequestBody @Valid UsuarioRequest dto) {
        Usuario usuario = dto.toModel();
        // Criptografa a senha antes de salvar
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuarioRepository.save(usuario);
        return ResponseEntity.ok(new UsuarioResponse(usuario));
    }

    // Atualizar usuário
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> atualizar(@PathVariable Long id,
                                                     @RequestBody @Valid UsuarioRequest dto) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setNome(dto.getNome());
                    usuario.setEmail(dto.getEmail());
                    // Criptografa a senha atualizada
                    usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
                    usuarioRepository.save(usuario);
                    return ResponseEntity.ok(new UsuarioResponse(usuario));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Deletar usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuarioRepository.delete(usuario);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
