package com.forohub.ForoHub.domain.topico;

import java.time.LocalDateTime;

public record DatosDetalleTopico(
        String titulo,
        String mensaje,
        LocalDateTime fecha_creacion,
        Boolean status,
        String autorNombre,
        String cursoNombre) {
    public DatosDetalleTopico(Topico topico) {
        this(topico.getTitulo(), topico.getMensaje(), topico.getFecha_creacion(),
                topico.getStatus(), topico.getAutor().getNombre(), topico.getCurso().getNombre());
    }
}
