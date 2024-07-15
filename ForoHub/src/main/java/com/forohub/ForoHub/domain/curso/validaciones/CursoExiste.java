package com.forohub.ForoHub.domain.curso.validaciones;

import com.forohub.ForoHub.domain.curso.CursoRepository;
import com.forohub.ForoHub.domain.curso.DatosCrearCurso;
import com.forohub.ForoHub.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CursoExiste implements ValidadorCurso {

    @Autowired
    private CursoRepository cursoRepository;

    @Override
    public void validarCurso(DatosCrearCurso datos) {
        boolean existeCurso = cursoRepository.existsByNombre(datos.nombre());

        if (existeCurso) {
            throw new ValidacionDeIntegridad("Ya existe un curso con ese titulo");
        }
    }
}