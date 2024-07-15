package com.forohub.ForoHub.domain.topico;


import java.time.LocalDateTime;

public record DatosListaTopico(
        String titulo,
        String mensaje,
        LocalDateTime fecha_creacion,
        Boolean status,
        String nombreAutor,
        String nombreCurso) {
    public DatosListaTopico(Topico topico) {
        this(topico.getTitulo(), topico.getMensaje(), topico.getFecha_creacion(),topico.getStatus(),
                topico.getAutor().getNombre(), topico.getCurso().getNombre());
    }

}
