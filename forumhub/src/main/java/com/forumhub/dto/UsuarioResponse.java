package com.forumhub.dto;

import com.forumhub.model.Usuario;

public class UsuarioResponse {

    private Long id;
    private String nome;
    private String email;

    public UsuarioResponse(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
    }

    // Getters só para resposta (normalmente não precisa setters)
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }
}
