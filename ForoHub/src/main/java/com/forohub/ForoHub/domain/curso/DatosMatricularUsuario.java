package com.forohub.ForoHub.domain.curso;

import java.util.List;

public record DatosMatricularUsuario(
        Long id,
        String nombre, String email, List<Long> cursos) {
}
