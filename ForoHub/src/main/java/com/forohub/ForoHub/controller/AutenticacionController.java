package com.forohub.ForoHub.controller;

import com.forohub.ForoHub.domain.usuario.DatosAutenticacionUsuario;
import com.forohub.ForoHub.domain.usuario.Usuario;
import com.forohub.ForoHub.infra.security.DatosJWTtoken;
import com.forohub.ForoHub.infra.security.TokenService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity autenticacionUsuario(@RequestBody @Valid DatosAutenticacionUsuario datos) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(datos.email(), datos.contrasena());
            var usuarioAutenticado = authenticationManager.authenticate(authentication);
            var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
            return ResponseEntity.ok(new DatosJWTtoken(JWTtoken));
    }
}

