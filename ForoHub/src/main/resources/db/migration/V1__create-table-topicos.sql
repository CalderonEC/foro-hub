CREATE TABLE Topicos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(255) NOT NULL,
    mensaje TEXT NOT NULL,
    fechaCreacion DATETIME NOT NULL,
    status BOOLEAN NOT NULL,
    autorId INT NOT NULL,
    cursoId INT NOT NULL,
    PRIMARY KEY (id)
);