package com.forohub.ForoHub.domain.usuario;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    UserDetails findByEmail(String email);

    Boolean existsByEmail(String email);

    List<Usuario> findAllByIdIn(Set<Long> ids);

    @Modifying
    @Transactional
    @Query("UPDATE Usuario u SET u.cursos = :cursos WHERE u.id = :usuario_id")
    void actualizarCursos(@Param("usuario_id") Long usuarioId, @Param("cursos") List<Long> cursos);

}
