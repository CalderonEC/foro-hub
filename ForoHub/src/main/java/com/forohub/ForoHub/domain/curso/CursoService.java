package com.forohub.ForoHub.domain.curso;

import com.forohub.ForoHub.domain.curso.validaciones.ValidadorCurso;
import com.forohub.ForoHub.domain.topico.Topico;
import com.forohub.ForoHub.domain.usuario.DatosListaUsuario;
import com.forohub.ForoHub.domain.usuario.Usuario;
import com.forohub.ForoHub.domain.usuario.UsuarioRepository;
import com.forohub.ForoHub.infra.errores.ValidacionDeIntegridad;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuariosCursosRepository usuariosCursosRepository;

    @Autowired
    private List<ValidadorCurso> validarCurso;

    public Curso crearCurso(DatosCrearCurso datos) {
        validarCurso.forEach(validador -> validador.validarCurso(datos));
        Curso curso = new Curso();
        curso.setId(curso.getId());
        curso.setNombre(datos.nombre());
        curso.setCategoria(datos.categoria());

        return cursoRepository.save(curso);
    }

    @Transactional
    public DatosMatricularUsuario inscribirUsuario(Long curso_id, Long usuario_id) {
        Curso curso = cursoRepository.findById(curso_id)
                .orElseThrow(() -> new ValidacionDeIntegridad("Curso no encontrado con id: " + curso_id));

        Usuario usuario = usuarioRepository.findById(usuario_id)
                .orElseThrow(() -> new ValidacionDeIntegridad("Usuario no encontrado con id: " + usuario_id));

        if (usuario.getCursos().contains(curso_id)) {
            throw new RuntimeException("El usuario ya está inscrito en este curso.");
        }

        usuario.getCursos().add(curso_id);
//        usuario.getCursos().add(curso_id);
        usuarioRepository.save(usuario);

        return new DatosMatricularUsuario(usuario.getId(), usuario.getNombre(), usuario.getEmail(), usuario.getCursos());
    }



    public Page<DatosListaUsuario> listarUsuariosInscritos(Long curso_id, Pageable paginacion) {
        Curso curso = cursoRepository.findById(curso_id)
                .orElseThrow(() -> new ValidacionDeIntegridad("Curso no encontrado con id: " + curso_id));

        Set<Long> usuarioIds = curso.getUsuarios().stream()
                .map(Usuario::getId)
                .collect(Collectors.toSet());

        if (usuarioIds.isEmpty()) {
            return Page.empty(paginacion);
        }

        List<Usuario> usuariosInscritos = usuarioRepository.findAllByIdIn(usuarioIds);

        int start = (int) paginacion.getOffset();
        int end = Math.min((start + paginacion.getPageSize()), usuariosInscritos.size());

        List<DatosListaUsuario> datosListaUsuarios = usuariosInscritos.subList(start, end).stream()
                .map(usuario -> new DatosListaUsuario(
                        usuario.getId(),
                        usuario.getNombre(),
                        usuario.getEmail(),
                        usuario.getTopicos().stream()
                                .map(Topico::getTitulo)
                                .collect(Collectors.toList()),
                        obtenerNombresCursos(usuario.getCursos())
                ))
                .collect(Collectors.toList());

        return new PageImpl<>(datosListaUsuarios, paginacion, usuariosInscritos.size());
    }

    private List<String> obtenerNombresCursos(List<Long> curso_ids) {
        if (curso_ids.isEmpty()) {
            return Collections.emptyList();
        }

        return curso_ids.stream()
                .map(curso_id -> cursoRepository.findById(curso_id)
                        .orElseThrow(() -> new ValidacionDeIntegridad("Curso no encontrado con id: " + curso_id)))
                .map(Curso::getNombre)
                .collect(Collectors.toList());
    }

    public void eliminarUsuarioDeCurso(Long curso_id, Long usuario_id) {
        Usuario usuario = usuarioRepository.findById(usuario_id)
                .orElseThrow(() -> new ValidacionDeIntegridad("Usuario no encontrado con id: " + usuario_id));

        List<Long> nuevosUsuarios = usuario.getCursos().stream()
                .filter(id -> !id.equals(curso_id))
                .collect(Collectors.toList());

        usuario.setCursos(nuevosUsuarios);
        usuarioRepository.save(usuario);
    }


    public Curso buscarPorId(Long id) {
        return cursoRepository.findById(id).orElse(null);
    }

    public void guardar(Curso curso) {
        cursoRepository.save(curso);
    }


}
