package com.forohub.ForoHub.domain.usuario;

import com.forohub.ForoHub.domain.curso.Curso;
import com.forohub.ForoHub.domain.curso.CursoRepository;
import com.forohub.ForoHub.domain.topico.Topico;
import com.forohub.ForoHub.domain.usuario.validaciones.ValidadorUsuario;
import com.forohub.ForoHub.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private List<ValidadorUsuario> validador;

    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioService(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public DatosRespuestaUsuario crearUsuario(DatosCrearUsuario datos,
                                              UriComponentsBuilder uriComponentsBuilder) {
        validador.forEach(validador -> validador.validarUser(datos));

        String contrasenaEncriptada = passwordEncoder.encode(datos.contrasena());

        Usuario nuevoUsuario = new Usuario(
                datos.nombre(),
                datos.email(),
                contrasenaEncriptada
        );
        usuarioRepository.save(nuevoUsuario);

        DatosRespuestaUsuario datosRespuesta = new DatosRespuestaUsuario(
                nuevoUsuario.getId(),
                nuevoUsuario.getNombre(),
                nuevoUsuario.getEmail()
        );

        URI url = uriComponentsBuilder.path("/usuarios/{id}")
                .buildAndExpand(nuevoUsuario.getId()).toUri();

        return datosRespuesta;
    }

    public ResponseEntity<Page<DatosListaUsuario>> listarUsuarios(Pageable paginacion) {
        Page<Usuario> usuariosPage = usuarioRepository.findAll(paginacion);
        Page<DatosListaUsuario> datosPage = usuariosPage.map(usuario -> new DatosListaUsuario(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                obtenerTitulosTopicos(usuario),
                obtenerNombresCursos(usuario.getCursos())
        ));

        return ResponseEntity.ok(datosPage);
    }

    private List<String> obtenerTitulosTopicos(Usuario usuario) {
        return usuario.getTopicos().stream()
                .map(Topico::getTitulo)
                .collect(Collectors.toList());
    }

    private List<String> obtenerNombresCursos(List<Long> curso_id) {
        return cursoRepository.findAllById(curso_id).stream()
                .map(Curso::getNombre)
                .collect(Collectors.toList());
    }

    public Usuario actualizarUsuario(Long usuario_id, DatosActualizarUsuario datos) {
        Usuario usuario = usuarioRepository.findById(usuario_id)
                .orElseThrow(() -> new ValidacionDeIntegridad("Usuario no encontrado con id: " + usuario_id));

        if (datos.nombre() != null && !datos.nombre().isEmpty()) {
            usuario.setNombre(datos.nombre());
        }

        if (datos.email() != null && !datos.email().isEmpty()) {
            usuario.setEmail(datos.email());
        }

        if (datos.contrasena() != null && !datos.contrasena().isEmpty()) {
            String contrasenaEncriptada = passwordEncoder.encode(datos.contrasena());
            usuario.setContrasena(contrasenaEncriptada);
        }

        return usuarioRepository.save(usuario);
    }

    public boolean eliminarUsuario(Long usuarioId) {
        if (usuarioRepository.existsById(usuarioId)) {
            usuarioRepository.deleteById(usuarioId);
            return true;
        } else {
            return false;
        }
    }
}

