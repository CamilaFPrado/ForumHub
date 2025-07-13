package com.forumhub.dto;

import com.forumhub.model.Curso;

public class CursoResponse {

    private Long id;
    private String nome;
    private String categoria;

    public CursoResponse(Curso curso) {
        this.id = curso.getId();
        this.nome = curso.getNome();
        this.categoria = curso.getCategoria();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCategoria() {
        return categoria;
    }
}
