package com.forohub.ForoHub.domain.curso;

import jakarta.persistence.*;

@Entity
public class UsuariosCursos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id")
    private Long usuarioId;
    @Column(name = "curso_id")
    private Long cursoId;

    public UsuariosCursos(Long usuarioId, Long cursoId) {
        this.usuarioId = usuarioId;
        this.cursoId = cursoId;
    }
}
