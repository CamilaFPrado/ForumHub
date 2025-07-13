package com.forumhub.controller;

import com.forumhub.dto.CursoRequest;
import com.forumhub.dto.CursoResponse;
import com.forumhub.model.Curso;
import com.forumhub.repository.CursoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    public ResponseEntity<List<CursoResponse>> listar() {
        List<Curso> cursos = cursoRepository.findAll();
        List<CursoResponse> resposta = cursos.stream()
                .map(CursoResponse::new)
                .toList();
        return ResponseEntity.ok(resposta);
    }

    @PostMapping
    public ResponseEntity<CursoResponse> cadastrar(@RequestBody @Valid CursoRequest dto) {
        Curso curso = dto.toModel();
        cursoRepository.save(curso);
        return ResponseEntity.ok(new CursoResponse(curso));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoResponse> buscarPorId(@PathVariable Long id) {
        return cursoRepository.findById(id)
                .map(curso -> ResponseEntity.ok(new CursoResponse(curso)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoResponse> atualizar(@PathVariable Long id, @RequestBody @Valid CursoRequest dto) {
        return cursoRepository.findById(id)
                .map(curso -> {
                    curso.setNome(dto.getNome());
                    curso.setCategoria(dto.getCategoria());
                    cursoRepository.save(curso);
                    return ResponseEntity.ok(new CursoResponse(curso));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        return cursoRepository.findById(id)
                .map(curso -> {
                    cursoRepository.delete(curso);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
