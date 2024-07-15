package com.forohub.ForoHub.domain.curso;

import com.forohub.ForoHub.domain.topico.Topico;

import java.util.List;

public record DatosCurso(Long id, String nombre, Categoria categoria, List<Topico> topicos) {

    public DatosCurso(Curso curso) {
        this(curso.getId(), curso.getNombre(), curso.getCategoria(), curso.getTopicos());
    }

}
