package com.forohub.ForoHub.domain.curso;

import jakarta.validation.constraints.NotNull;

public record DatosCrearCurso(
        @NotNull
        Long id,
        @NotNull
        String nombre,
        @NotNull
        Categoria categoria
) {
}
