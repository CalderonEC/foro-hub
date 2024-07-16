ForoHub es una aplicación desarrollada en Spring Boot que permite la gestión de tópicos y cursos, con funcionalidades de autenticación y manejo de usuarios.

## Requisitos previos

- Java 11 o superior
- Maven 3.6.3 o superior
- MySQL

## Instalación

1. Clona el repositorio:
   ```bash
   git clone https://github.com/CalderonEC/foro-hub
   cd ForoHub

2. Configura las variables de entorno para la base de datos:

spring.datasource.url={DATASOURCE_URL}
spring.datasource.username={DATASOURCE_USERNAME}
spring.datasource.password={DATASOURCE_PASSWORD}

## Estructura del proyecto

- `src/main/java/com/forohub/ForoHub/ForoHubApplication.java`: Clase principal de la aplicación.
- `domain/topico/`: Clases relacionadas con los tópicos.
  - **DatosActualizarTopico.java**: DTO para actualizar un tópico.
  - **DatosCrearTopico.java**: DTO para crear un nuevo tópico.
  - **DatosDetalleTopico.java**: DTO para detalles de un tópico.
  - **DatosListaTopico.java**: DTO para listar tópicos.
  - **DatosRespuestaTopico.java**: DTO para respuestas de un tópico.
  - **TopicoService.java**: Servicio para la lógica de negocio de los tópicos.
  - **Topico.java**: Entidad que representa un tópico.
  - **TopicoRepository.java**: Repositorio para acceder a los datos de tópicos.
  - **validaciones/**: Clases de validación.
    - **ValidadorTopico.java**: Validador para tópicos.
    - **UsuarioExistente.java**: Validador para la existencia de usuarios.
    - **TopicoExistente.java**: Validador para la existencia de tópicos.
- `db/migration/`: Scripts de migración de la base de datos.
  - **V1__create-table-topicos.sql**: Script para crear la tabla de tópicos.
  - **V2__create-table-cursos.sql**: Script para crear la tabla de cursos.
  - **V3__update-table-topicos.sql**: Script para actualizar la tabla de tópicos.
  - **V4__create-table-usuarios.sql**: Script para crear la tabla de usuarios.
  - **V5__create-table-respuestas.sql**: Script para crear la tabla de respuestas.
  - **V6__update-table-topicos.sql**: Script para actualizar la tabla de tópicos.
  - **V7__update-table-cursos.sql**: Script para actualizar la tabla de cursos.
  - **V8__create_table-usuario_curso.sql**: Script para crear la tabla de relación usuario-curso.
  - **V9__update-table-usuarios.sql**: Script para actualizar la tabla de usuarios.
  - **V10__update-table-cursos.sql**: Script para actualizar la tabla de cursos.
  - **V11__delete-table-respuestas.sql**: Script para eliminar la tabla de respuestas.
- `infra/errores/`: Manejo de errores.
  - **ValidacionDeIntegridad.java**: Clase para validar integridad de datos.
  - **TratadorDeErrores.java**: Clase para manejar errores globalmente.
- `infra/security/`: Configuraciones de seguridad y autenticación.
  - **AutenticacionService.java**: Servicio de autenticación.
  - **SecurityConfigurations.java**: Configuraciones de seguridad.
  - **DatosJWTtoken.java**: Clase para manejar datos de token JWT.
  - **TokenService.java**: Servicio para manejar tokens JWT.
  - **SecurityFilter.java**: Filtro de seguridad para autenticación.
- `infra/springdoc/`: Configuraciones de SpringDoc para documentación de la API.

## Endpoints principales

- **GET /topicos/{id}**: Obtener el detalle de un tópico por su ID.
- **POST /topicos**: Crear un nuevo tópico (requiere autenticación).
- **PUT /topicos/{id}**: Actualizar un tópico existente.
- **DELETE /topicos/{id}**: Eliminar un tópico.

## Autenticación

La autenticación se maneja mediante tokens JWT. Asegúrate de configurar las claves y los servicios correspondientes en `SecurityConfigurations.java` y `TokenService.java`.

## Documentación de API
 
La documentación de la API se genera automáticamente utilizando SpringFox Swagger. Puedes acceder a ella en: http://localhost:8080/swagger-ui.html
