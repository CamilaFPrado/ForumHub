package com.forumhub.repository;

import com.forumhub.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    boolean existsByTituloAndMensagem(String titulo, String mensagem);

    Optional<Topico> findByTituloAndMensagem(String titulo, String mensagem);
}
