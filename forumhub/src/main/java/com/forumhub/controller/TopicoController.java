package com.forumhub.controller;

import com.forumhub.dto.TopicoRequest;
import com.forumhub.dto.TopicoUpdateRequest;
import com.forumhub.model.Topico;
import com.forumhub.repository.TopicoRepository;
import java.util.List;
import com.forumhub.dto.TopicoResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody @Valid TopicoRequest dto) {
        // Verifica duplicidade
        boolean existeTopico = topicoRepository.findByTituloAndMensagem(dto.getTitulo(), dto.getMensagem()).isPresent();
        if (existeTopico) {
            return ResponseEntity.badRequest().body("Já existe um tópico com esse título e mensagem.");
        }

        Topico topico = dto.toModel();
        topicoRepository.save(topico);
        return ResponseEntity.ok(topico);
    }


    @GetMapping
    public ResponseEntity<List<TopicoResponse>> listar() {
        List<Topico> topicos = topicoRepository.findAll();
        List<TopicoResponse> resposta = topicos.stream()
                .map(TopicoResponse::new)
                .toList();
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoResponse> buscarPorId(@PathVariable Long id) {
        return topicoRepository.findById(id)
                .map(topico -> ResponseEntity.ok(new TopicoResponse(topico)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicoResponse> atualizar(@PathVariable Long id,
                                                    @RequestBody @Valid TopicoUpdateRequest dto) {
        return topicoRepository.findById(id)
                .map(topico -> {
                    topico.setTitulo(dto.getTitulo());
                    topico.setMensagem(dto.getMensagem());
                    topicoRepository.save(topico);
                    return ResponseEntity.ok(new TopicoResponse(topico));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        return topicoRepository.findById(id)
                .map(topico -> {
                    topicoRepository.delete(topico);
                    return ResponseEntity.noContent().build(); // 204 No Content
                })
                .orElse(ResponseEntity.notFound().build()); // 404 Not Found
    }




}
