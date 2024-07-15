package com.forohub.ForoHub.domain.topico;

import com.forohub.ForoHub.domain.curso.Curso;
import com.forohub.ForoHub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;
    @JoinColumn(name = "fecha_creacion")
    private LocalDateTime fecha_creacion = LocalDateTime.now();
    private Boolean status;
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;
    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;


    public Topico(String titulo, String mensaje , Usuario autor, Curso curso) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.fecha_creacion = LocalDateTime.now();
        this.status = false;
        this.autor = autor;
        this.curso = curso;
    }


}
