package com.forumhub.controller;

import com.forumhub.dto.RespostaRequest;
import com.forumhub.dto.RespostaResponse;
import com.forumhub.model.Resposta;
import com.forumhub.model.Topico;
import com.forumhub.model.Usuario;
import com.forumhub.repository.RespostaRepository;
import com.forumhub.repository.TopicoRepository;
import com.forumhub.repository.UsuarioRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/respostas")
public class RespostaController {

    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Listar todas as respostas
    @GetMapping
    public ResponseEntity<List<RespostaResponse>> listar() {
        List<Resposta> respostas = respostaRepository.findAll();
        List<RespostaResponse> respostaResponses = respostas.stream()
                .map(RespostaResponse::new)
                .toList();
        return ResponseEntity.ok(respostaResponses);
    }

    // Buscar resposta por id
    @GetMapping("/{id}")
    public ResponseEntity<RespostaResponse> buscarPorId(@PathVariable Long id) {
        return respostaRepository.findById(id)
                .map(resposta -> ResponseEntity.ok(new RespostaResponse(resposta)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Cadastrar uma nova resposta
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody @Valid RespostaRequest dto) {
        // Verificar se tópico existe
        Topico topico = topicoRepository.findById(dto.getTopicoId()).orElse(null);
        if (topico == null) {
            return ResponseEntity.badRequest().body("Tópico não encontrado.");
        }

        // Verificar se autor existe
        Usuario autor = usuarioRepository.findById(dto.getAutorId()).orElse(null);
        if (autor == null) {
            return ResponseEntity.badRequest().body("Autor não encontrado.");
        }

        // Monta o objeto usando toModel
        Resposta resposta = dto.toModel(topico, autor);
        respostaRepository.save(resposta);

        return ResponseEntity.ok(new RespostaResponse(resposta));
    }


    // Atualizar resposta
    @PutMapping("/{id}")
    public ResponseEntity<RespostaResponse> atualizar(@PathVariable Long id,
                                                      @RequestBody @Valid RespostaRequest dto) {
        return respostaRepository.findById(id)
                .map(resposta -> {
                    resposta.setMensagem(dto.getMensagem());

                    // Atualiza o tópico se existir
                    topicoRepository.findById(dto.getTopicoId())
                            .ifPresent(resposta::setTopico);

                    // Atualiza o autor se existir
                    usuarioRepository.findById(dto.getAutorId())
                            .ifPresent(resposta::setAutor);

                    respostaRepository.save(resposta);
                    return ResponseEntity.ok(new RespostaResponse(resposta));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Deletar resposta
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        return respostaRepository.findById(id)
                .map(resposta -> {
                    respostaRepository.delete(resposta);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
