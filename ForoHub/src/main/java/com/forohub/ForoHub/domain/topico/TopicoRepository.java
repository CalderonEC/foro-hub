package com.forohub.ForoHub.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {

    Boolean existsByTituloOrMensaje(String titulo, String mensaje);

    Page<Topico> findAll(Pageable paginacion);
}
