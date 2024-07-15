package com.forohub.ForoHub.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DatosActualizarUsuario(
        @NotBlank(message = "El nombre no puede estar en blanco")
        String nombre,
        @Email(message = "El email debe ser válido")
        @NotBlank(message = "El email no puede estar en blanco")
        String email,
        @NotBlank(message = "La contraseña no puede estar en blanco")
        String contrasena) {
}
