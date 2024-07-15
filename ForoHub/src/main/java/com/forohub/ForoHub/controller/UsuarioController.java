package com.forohub.ForoHub.controller;

import com.forohub.ForoHub.domain.usuario.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/foro_hub/usuarios")
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService service;

    @PostMapping("/registrar")
    public ResponseEntity<DatosRespuestaUsuario> crearUsuario(@RequestBody @Valid DatosCrearUsuario datosCrearUsuario,
                                                              UriComponentsBuilder uriComponentsBuilder) {
        DatosRespuestaUsuario responseEntity = service.crearUsuario(datosCrearUsuario, uriComponentsBuilder);
        return ResponseEntity.ok(responseEntity);
    }

    @GetMapping("/listado")
    public ResponseEntity<Page<DatosListaUsuario>> listarUsuarios(@RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return service.listarUsuarios(pageable);
    }

    @PutMapping("/{usuario_id}/actualizar")
    public ResponseEntity<Usuario> actualizarUsuario(
            @PathVariable Long usuario_id,
            @RequestBody DatosActualizarUsuario datos) {

        Usuario usuarioActualizado = service.actualizarUsuario(usuario_id, datos);
        return ResponseEntity.ok(usuarioActualizado);
    }


    @DeleteMapping("/{id}/eliminar")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        boolean eliminado = service.eliminarUsuario(id);
        if (eliminado) {
            return ResponseEntity.ok("Usuario con ID " + id + " eliminado correctamente.");
        } else {
            return ResponseEntity.badRequest().body("Usuario con ID " + id + " no encontrado.");
        }
    }

}
