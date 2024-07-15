package com.forohub.ForoHub.controller;

import com.forohub.ForoHub.domain.curso.Curso;
import com.forohub.ForoHub.domain.curso.CursoService;
import com.forohub.ForoHub.domain.curso.DatosCrearCurso;
import com.forohub.ForoHub.domain.curso.DatosMatricularUsuario;
import com.forohub.ForoHub.domain.usuario.DatosListaUsuario;
import com.forohub.ForoHub.domain.usuario.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cursos")
@SecurityRequirement(name = "bearer-key")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/crear")
    @Transactional
    public ResponseEntity<Curso> crearCurso(@RequestBody DatosCrearCurso crearCurso) {
        Curso curso = cursoService.crearCurso(crearCurso);
        return ResponseEntity.ok(curso);
    }

    @PostMapping("/{curso_id}/inscribir/{usuario_id}")
    public ResponseEntity<DatosMatricularUsuario> inscribirUsuario(@PathVariable Long curso_id, @PathVariable Long usuario_id) {
        DatosMatricularUsuario datosUsuarioConCursos = cursoService.inscribirUsuario(curso_id, usuario_id);
        return ResponseEntity.ok(datosUsuarioConCursos);
    }


    @GetMapping("/{curso_id}/usuarios")
    public Page<DatosListaUsuario> listarUsuariosInscritos(
            @PathVariable("curso_id") Long cursoId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable paginacion = PageRequest.of(page, size);
        return cursoService.listarUsuariosInscritos(cursoId, paginacion);
    }

    @DeleteMapping("/{cursoId}/usuarios/{usuarioId}")
    @Transactional
    public ResponseEntity<String> eliminarUsuarioDeCurso(
            @PathVariable Long cursoId,
            @PathVariable Long usuarioId) {

        cursoService.eliminarUsuarioDeCurso(cursoId, usuarioId);

        return ResponseEntity.ok("Usuario eliminado correctamente del curso.");
    }
}
