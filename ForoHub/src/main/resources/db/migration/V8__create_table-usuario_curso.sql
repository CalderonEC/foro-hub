CREATE TABLE usuarios_cursos (
    usuario_id BIGINT NOT NULL,
    curso_id BIGINT NOT NULL,
    PRIMARY KEY (usuario_id, curso_id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY (curso_id) REFERENCES cursos(id)
);