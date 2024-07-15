package com.forohub.ForoHub.domain.topico.validaciones;

import com.forohub.ForoHub.domain.topico.DatosCrearTopico;
import com.forohub.ForoHub.domain.topico.TopicoRepository;
import com.forohub.ForoHub.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopicoExistente implements ValidadorTopico{

    @Autowired
    TopicoRepository topicoRepository;

    @Override
    public void validar(DatosCrearTopico datos) {
        boolean existeTopico = topicoRepository.existsByTituloOrMensaje(datos.titulo(), datos.mensaje());

        if (existeTopico) {
            throw new ValidacionDeIntegridad("Ya existe un topicoId con ese titulo o mensaje.");
        }
    }
}
