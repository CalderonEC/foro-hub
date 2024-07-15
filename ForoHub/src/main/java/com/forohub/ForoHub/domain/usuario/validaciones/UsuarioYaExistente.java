package com.forohub.ForoHub.domain.usuario.validaciones;

import com.forohub.ForoHub.domain.usuario.DatosCrearUsuario;
import com.forohub.ForoHub.domain.usuario.UsuarioRepository;
import com.forohub.ForoHub.infra.errores.ValidacionDeIntegridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioYaExistente implements ValidadorUsuario {

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    public void validarUser(DatosCrearUsuario datos) {
        boolean existe = usuarioRepository.existsByEmail(datos.email());

        if (existe) {
            throw new ValidacionDeIntegridad("El correo electrónico utilizado ya se encuentra registrado");
        }
    }
}
