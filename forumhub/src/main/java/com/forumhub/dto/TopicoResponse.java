package com.forumhub.dto;

import com.forumhub.model.Topico;

public class TopicoResponse {

    private Long id;
    private String titulo;
    private String mensagem;
    private String nomeAutor;
    private String nomeCurso;

    public TopicoResponse(Topico topico){
        this.id = topico.getId();
        this.titulo = topico.getTitulo();
        this.mensagem = topico.getMensagem();
        this.nomeAutor = topico.getAutor().getNome();
        this.nomeCurso = topico.getCurso().getNome();

    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }


}