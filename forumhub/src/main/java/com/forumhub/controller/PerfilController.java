package com.forumhub.controller;

import com.forumhub.dto.PerfilRequest;
import com.forumhub.dto.PerfilResponse;
import com.forumhub.model.Perfil;
import com.forumhub.repository.PerfilRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/perfis")
public class PerfilController {

    @Autowired
    private PerfilRepository perfilRepository;

    // Listar todos os perfis
    @GetMapping
    public ResponseEntity<List<PerfilResponse>> listar() {
        List<Perfil> perfis = perfilRepository.findAll();
        List<PerfilResponse> resposta = perfis.stream()
                .map(PerfilResponse::new)
                .toList();
        return ResponseEntity.ok(resposta);
    }

    // Buscar perfil por ID
    @GetMapping("/{id}")
    public ResponseEntity<PerfilResponse> buscarPorId(@PathVariable Long id) {
        return perfilRepository.findById(id)
                .map(perfil -> ResponseEntity.ok(new PerfilResponse(perfil)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Cadastrar novo perfil
    @PostMapping
    public ResponseEntity<PerfilResponse> cadastrar(@RequestBody @Valid PerfilRequest dto) {
        Perfil perfil = dto.toModel();
        perfilRepository.save(perfil);
        return ResponseEntity.ok(new PerfilResponse(perfil));
    }

    // Atualizar perfil
    @PutMapping("/{id}")
    public ResponseEntity<PerfilResponse> atualizar(@PathVariable Long id,
                                                    @RequestBody @Valid PerfilRequest dto) {
        return perfilRepository.findById(id)
                .map(perfil -> {
                    perfil.setNome(dto.getNome());
                    perfilRepository.save(perfil);
                    return ResponseEntity.ok(new PerfilResponse(perfil));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Deletar perfil
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        return perfilRepository.findById(id)
                .map(perfil -> {
                    perfilRepository.delete(perfil);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
