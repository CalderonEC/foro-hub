package com.forohub.ForoHub.domain.topico;


import java.time.LocalDateTime;

public record DatosRespuestaTopico(
        String titulo,
        String mensaje,
        LocalDateTime fecha_creacion) {}
