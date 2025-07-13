package com.forumhub.dto;

import com.forumhub.model.Perfil;

public class PerfilResponse {

    private Long id;
    private String nome;

    public PerfilResponse(Perfil perfil) {
        this.id = perfil.getId();
        this.nome = perfil.getNome();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}
