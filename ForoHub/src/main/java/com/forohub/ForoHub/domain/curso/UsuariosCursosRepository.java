package com.forohub.ForoHub.domain.curso;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuariosCursosRepository extends JpaRepository<UsuariosCursos, Long> {
    boolean existsByUsuarioIdAndCursoId(Long usuarioId, Long cursoId);
}
