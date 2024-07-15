package com.forohub.ForoHub.controller;

import com.forohub.ForoHub.domain.topico.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;



@RestController
@RequestMapping("/foro_hub")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    private TopicoService service;

    @PostMapping("/registrar-topicos")
    public ResponseEntity<DatosRespuestaTopico> registrarTopico(@Valid @RequestBody DatosCrearTopico datos,
                                                                UriComponentsBuilder uriComponentsBuilder) {
        return service.registrarTopico(datos, uriComponentsBuilder);
    }


    @GetMapping("/topicos")
    @Operation(summary = "Obtener todos los tópicos paginados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de tópicos encontrados"),
            @ApiResponse(responseCode = "404", description = "No se encontraron tópicos")
    })
    public ResponseEntity<Page<DatosListaTopico>> listarTopicos(@RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return service.listarTopicos(pageable);
    }

    @GetMapping("topicos/{id}")
    public ResponseEntity<DatosDetalleTopico> obtenerDetalleTopico(@PathVariable Long id) {
        DatosDetalleTopico detalleTopico = service.obtenerDetalleTopico(id);
        return ResponseEntity.ok(detalleTopico);
    }

    @PutMapping("topicos/{id}")
    public ResponseEntity<DatosDetalleTopico> actualizarTopico(@PathVariable Long id, @RequestBody @Valid DatosActualizarTopico actualizarTopico) {
        DatosDetalleTopico datosDetalleTopico = service.actualizarTopico(id, actualizarTopico);
        return ResponseEntity.ok(datosDetalleTopico);
    }

    @DeleteMapping("topicos/{id}")
    public ResponseEntity<String> eliminarTopico(@PathVariable Long id) {
        boolean eliminado = service.eliminarTopico(id);
        if (eliminado) {
            return ResponseEntity.ok("Tópico eliminado correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró ningún tópico con el ID proporcionado.");
        }
    }

}

