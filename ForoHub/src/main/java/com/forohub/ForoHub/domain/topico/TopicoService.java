package com.forohub.ForoHub.domain.topico;

import com.forohub.ForoHub.domain.curso.Curso;
import com.forohub.ForoHub.domain.curso.CursoRepository;
import com.forohub.ForoHub.domain.usuario.Usuario;
import com.forohub.ForoHub.domain.usuario.UsuarioRepository;
import com.forohub.ForoHub.domain.topico.validaciones.ValidadorTopico;
import com.forohub.ForoHub.infra.errores.ValidacionDeIntegridad;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;


@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private List<ValidadorTopico> validarTopico;

    private static final Logger logger = LoggerFactory.getLogger(TopicoService.class);


    public ResponseEntity<DatosRespuestaTopico> registrarTopico(@Valid DatosCrearTopico datos,
                                                                UriComponentsBuilder uriComponentsBuilder) {
        try {
            Curso curso = cursoRepository.findById(datos.curso_id())
                    .orElseThrow(() -> new EntityNotFoundException("El curso especificado no existe."));


            Usuario autor = usuarioRepository.findById(datos.autor_id())
                    .orElseThrow(() -> new EntityNotFoundException("El usuario especificado no existe."));

            for (ValidadorTopico validador : validarTopico) {
                validador.validar(datos);
            }

            Topico nuevoTopico = new Topico(datos.titulo(), datos.mensaje() , autor, curso);
            topicoRepository.save(nuevoTopico);


            DatosRespuestaTopico datosRespuesta = new DatosRespuestaTopico(nuevoTopico.getTitulo(),
                    nuevoTopico.getMensaje(), nuevoTopico.getFecha_creacion());

            URI url = uriComponentsBuilder.path("/topicos/{id}")
                    .buildAndExpand(nuevoTopico.getId()).toUri();
            logger.info("Tópico registrado con éxito: {}", url);

            return ResponseEntity.created(url).body(datosRespuesta);
        } catch (EntityNotFoundException e) {
            logger.error("Error al registrar tópico: {}", e.getMessage());
            throw e;
        }
    }


    public ResponseEntity<Page<DatosListaTopico>> listarTopicos(@PageableDefault(size = 10) Pageable paginacion) {
        Page<Topico> topicosPage = topicoRepository.findAll(paginacion);
        Page<DatosListaTopico> datosPage = topicosPage.map(DatosListaTopico::new);

        return ResponseEntity.ok(datosPage);
    }

    public DatosDetalleTopico obtenerDetalleTopico(Long id) {
        var topico = topicoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tópico no encontrado"));
        return new DatosDetalleTopico(topico);
    }

    public DatosDetalleTopico actualizarTopico(Long id, DatosActualizarTopico actualizarTopico) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ValidacionDeIntegridad("Tópico no encontrado"));

        if (!cursoRepository.existsById(actualizarTopico.curso_id())) {
            throw new ValidacionDeIntegridad("Curso no encontrado");
        }

        topico.setTitulo(actualizarTopico.titulo());
        topico.setMensaje(actualizarTopico.mensaje());
        topico.setCurso(cursoRepository.findById(actualizarTopico.curso_id()).get());

        topicoRepository.save(topico);

        return new DatosDetalleTopico(topico);
    }

    public boolean eliminarTopico(Long id) {
        Optional<Topico> optionalTopico = topicoRepository.findById(id);
        if (optionalTopico.isPresent()) {
            topicoRepository.deleteById(id);
            return true;
        }
        return false;
    }

}