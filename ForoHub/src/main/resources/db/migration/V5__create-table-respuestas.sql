CREATE TABLE respuestas (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    mensaje TEXT NOT NULL,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    solucion BOOLEAN NOT NULL DEFAULT FALSE,
    topico_id BIGINT,
    autor_id BIGINT,
    FOREIGN KEY (topico_id) REFERENCES topicos(id),
    FOREIGN KEY (autor_id) REFERENCES usuarios(id)
);