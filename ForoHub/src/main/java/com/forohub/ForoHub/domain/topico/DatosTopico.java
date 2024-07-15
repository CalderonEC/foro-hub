package com.forohub.ForoHub.domain.topico;

import java.time.LocalDateTime;

public record DatosTopico(Long id,
                          String titulo,
                          String mensaje,
                          LocalDateTime fecha_creacion,
                          Boolean status,
                          String autor,
                          String curso) {


    public DatosTopico(Topico topico) {
        this(topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFecha_creacion(),
                topico.getStatus(),
                topico.getAutor().getNombre(),
                topico.getCurso().getNombre());
    }
}
