package com.forumhub.dto;
import com.forumhub.model.Resposta;
import com.forumhub.model.Topico;
import com.forumhub.model.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RespostaRequest {

    @NotBlank
    private String mensagem;

    @NotNull
    private Long topicoId;

    @NotNull
    private Long autorId;

    // Getters e Setters
    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Long getTopicoId() {
        return topicoId;
    }

    public void setTopicoId(Long topicoId) {
        this.topicoId = topicoId;
    }

    public Long getAutorId() {
        return autorId;
    }

    public void setAutorId(Long autorId) {
        this.autorId = autorId;
    }

    public Resposta toModel(Topico topico, Usuario autor) {
        Resposta resposta = new Resposta();
        resposta.setMensagem(this.mensagem);
        resposta.setTopico(topico);
        resposta.setAutor(autor);
        return resposta;
    }
}
