package com.forumhub.dto;
import com.forumhub.model.Perfil;
import jakarta.validation.constraints.NotBlank;

public class PerfilRequest {

    @NotBlank
    private String nome;

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Perfil toModel() {
        Perfil perfil = new Perfil();
        perfil.setNome(this.nome);
        return perfil;
    }
}
