package com.forohub.ForoHub.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record DatosCrearTopico(
        @NotBlank(message = "El título no puede estar en blanco")
        String titulo,
        @NotBlank(message = "El mensaje no puede estar en blanco")
        String mensaje,
        @NotNull(message = "El ID del autor es obligatorio")
        Long autor_id,
        @NotNull(message = "El ID del curso es obligatorio")
        Long curso_id
) {
}
