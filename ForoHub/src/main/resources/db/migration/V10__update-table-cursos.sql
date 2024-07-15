ALTER TABLE cursos
ADD COLUMN usuario_id BIGINT;

ALTER TABLE cursos
ADD CONSTRAINT fk_usuario_curso
FOREIGN KEY (usuario_id) REFERENCES usuarios(id);