package com.forumhub.dto;
import com.forumhub.model.Topico;
import com.forumhub.model.Usuario;
import com.forumhub.model.Curso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TopicoRequest {

    @NotBlank
    private String titulo;

    @NotBlank
    private String mensagem;

    @NotNull
    private Long autorId;

    @NotNull
    private Long cursoId;

    // Getters e Setters

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Long getAutorId() {
        return autorId;
    }

    public void setAutorId(Long autorId) {
        this.autorId = autorId;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }

    public Topico toModel() {
        Usuario autor = new Usuario();
        autor.setId(this.autorId);

        Curso curso = new Curso();
        curso.setId(this.cursoId);

        Topico topico = new Topico();
        topico.setTitulo(this.titulo);
        topico.setMensagem(this.mensagem);
        topico.setAutor(autor);
        topico.setCurso(curso);

        return topico;
    }


}
