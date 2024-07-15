package com.forohub.ForoHub.domain.usuario;

import jakarta.validation.constraints.NotBlank;

public record DatosCrearUsuario(
        @NotBlank
        String nombre,
        @NotBlank
        String email,
        @NotBlank
        String contrasena) {
}
