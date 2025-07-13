package com.forumhub.dto;
import com.forumhub.model.Curso;
import jakarta.validation.constraints.NotBlank;

public class CursoRequest {

    @NotBlank
    private String nome;

    @NotBlank
    private String categoria;

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Curso toModel() {
        Curso curso = new Curso();
        curso.setNome(this.nome);
        curso.setCategoria(this.categoria);
        return curso;
    }

}
