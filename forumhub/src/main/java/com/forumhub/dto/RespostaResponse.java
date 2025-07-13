package com.forumhub.dto;

import com.forumhub.model.Resposta;

public class RespostaResponse {

    private Long id;
    private String mensagem;
    private Long topicoId;
    private Long autorId;

    // Construtor que recebe a entidade Resposta e popula os campos do DTO
    public RespostaResponse(Resposta resposta) {
        this.id = resposta.getId();
        this.mensagem = resposta.getMensagem();
        this.topicoId = resposta.getTopico().getId();
        this.autorId = resposta.getAutor().getId();
    }

    // Getters (somente para leitura)
    public Long getId() {
        return id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public Long getTopicoId() {
        return topicoId;
    }

    public Long getAutorId() {
        return autorId;
    }
}
