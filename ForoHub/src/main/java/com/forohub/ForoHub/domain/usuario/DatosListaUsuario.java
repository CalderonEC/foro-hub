package com.forohub.ForoHub.domain.usuario;


import java.util.List;

public record DatosListaUsuario(
        Long id,
        String nombre,
        String email,
        List<String> titulosTopicos,
        List<String> nombrescursos) {
}
