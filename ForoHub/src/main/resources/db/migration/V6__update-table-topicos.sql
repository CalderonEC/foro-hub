ALTER TABLE topicos
MODIFY COLUMN autorId BIGINT NOT NULL;

ALTER TABLE topicos
MODIFY COLUMN cursoId BIGINT NOT NULL;

ALTER TABLE topicos
MODIFY COLUMN fechaCreacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE topicos
DROP FOREIGN KEY fk_topico_autor;

ALTER TABLE topicos
DROP FOREIGN KEY fk_topico_curso;

ALTER TABLE topicos
ADD CONSTRAINT fk_topico_autor FOREIGN KEY (autorId) REFERENCES usuarios(id);

ALTER TABLE topicos
ADD CONSTRAINT fk_topico_curso FOREIGN KEY (cursoId) REFERENCES cursos(id);
