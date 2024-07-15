package com.forohub.ForoHub.domain.topico.validaciones;

import com.forohub.ForoHub.domain.curso.CursoRepository;
import com.forohub.ForoHub.domain.topico.DatosCrearTopico;
import com.forohub.ForoHub.infra.errores.ValidacionDeIntegridad;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CursoExistente implements ValidadorTopico {

    @Autowired
    private CursoRepository cursoRepository;

    @Override
    public void validar(DatosCrearTopico datos) {
        if (datos.curso_id() == null) {
            throw new ValidacionDeIntegridad("El topicoId debe tener un curso asignado");
        }
        var curso = cursoRepository.findById(datos.curso_id());

        if (!curso.isPresent()) {
            throw new ValidationException("El curso al que desea agregar el topicoId no existe.");
        }
    }
}