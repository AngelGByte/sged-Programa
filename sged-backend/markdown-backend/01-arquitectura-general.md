# Arquitectura General del Backend — SGED

## ¿Qué es SGED?

**SGED** = Sistema Integral de Gestión Académica y Bienestar Estudiantil

Es un **monolito modular** construido con **Spring Boot**. Su trabajo es simple: recibe peticiones HTTP del frontend (React), las procesa según reglas de negocio, accede a la base de datos, y devuelve respuestas en JSON.

No genera HTML. Solo es una **API REST**.

---

## La Analogía del Restaurante

Para entender cómo funciona, imagina todo el sistema como un **restaurante**:

| Componente | Rol en el restaurante | Función |
|---|---|---|
| **Controller** | Mesero | Recibe la orden (petición HTTP), la lleva a la cocina (Service), y trae el plato de vuelta |
| **Service** | Chef | Aquí vive toda la magia. Decide cómo se prepara cada plato, valida ingredientes, aplica las reglas |
| **Repository** | Bodega | Accede a la base de datos. No decide nada: solo entrega o guarda lo que el Chef le pide |
| **Entity** | Ingredientes + Plato | Son los datos que se guardan en la BD |
| **DTO** | Ticket de la orden | Separamos lo que entra del cliente de lo que guardamos en la BD |

---

## Estructura del Backend

```
cl.duocuc.sged/
│
├── SgedApplication.java              ← El punto de entrada
│
├── config/                           ← Configuración del sistema
│   ├── SecurityConfig.java           ← Quién entra, quién no (portero)
│   ├── JwtConfig.java                ← Maneja tokens JWT (credenciales digitales)
│   ├── CorsConfig.java               ← Permite que el frontend hable con nosotros
│   ├── JwtAuthFilter.java            ← Valida cada petición
│   └── DataInitializer.java          ← Carga datos iniciales en la BD
│
├── exception/                        ← Manejo de errores
│   ├── ResourceNotFoundException.java ← "No encontré eso" (404)
│   ├── BadRequestException.java      ← "Los datos son inválidos" (400)
│   └── GlobalExceptionHandler.java   ← Receptor centralizado de todos los errores
│
├── usuario/                          ← Módulo de Usuarios y Autenticación
│   ├── entity/Usuario.java           ← Tabla de usuarios
│   ├── repository/UsuarioRepository.java
│   ├── service/UsuarioService.java
│   ├── controller/UsuarioController.java
│   └── dto/UsuarioDTO.java
│
├── curso/                            ← Módulo de Cursos
│   ├── entity/Curso.java
│   ├── repository/CursoRepository.java
│   ├── service/CursoService.java
│   └── controller/CursoController.java
│
├── calificacion/                     ← Módulo de Calificaciones
│   ├── entity/Calificacion.java
│   ├── repository/CalificacionRepository.java
│   ├── service/CalificacionService.java
│   └── controller/CalificacionController.java
│
├── asistencia/                       ← Módulo de Asistencias
│   ├── entity/Asistencia.java
│   ├── repository/AsistenciaRepository.java
│   ├── service/AsistenciaService.java
│   └── controller/AsistenciaController.java
│
├── dashboard/                        ← Módulo de Reportes y Estadísticas
│   ├── dto/DashboardDTO.java
│   ├── service/DashboardService.java
│   └── controller/DashboardController.java
│
├── lookup/                           ← Datos estáticos (futuro)
│
└── util/                             ← Utilidades (futuro)
```

---

## Flujo de una Petición HTTP

### Ejemplo: Crear una calificación

```
Cliente (Frontend)
  ↓
  POST /api/calificaciones
  {
    "estudianteId": 5,
    "nota": 6.5,
    ...
  }
  ↓
CORS Middleware → ¿Es del frontend permitido?
  ↓
JwtAuthFilter → ¿Tiene un token válido?
  ↓
SecurityConfig → ¿Tiene permiso? (¿Es DOCENTE o ADMIN?)
  ↓
CalificacionController (MESERO)
  "Ok, recibí la orden"
  → CalificacionService.crear(calificacion)
  ↓
CalificacionService (CHEF) 
  "Voy a validar esto"
  → Valida que el estudiante exista
  → Valida que la nota esté entre 1.0 y 7.0
  → CalificacionRepository.save(calificacion)
  ↓
CalificacionRepository (BODEGA)
  "Voy a guardarlo en la BD"
  → INSERT INTO calificaciones VALUES (...)
  ↓
Response (200 OK + JSON)
{
  "id": 125,
  "estudianteId": 5,
  "nota": 6.5,
  ...
}
```

---

## Patrones Aplicados

### 1. **Repository Pattern**
- Aísla la lógica de acceso a datos
- Facilita testing (mockear la BD)
- Cambiar de BD sin tocar la lógica de negocio

### 2. **DTO Pattern**
- Separa la estructura de entrada/salida de la entidad
- Valida datos antes de procesarlos
- Protege datos internos

### 3. **Service Pattern**
- Toda la lógica de negocio está aquí
- Reutilizable desde múltiples Controllers
- Fácil de testear

### 4. **Controller Pattern**
- Solo recibe, delega y responde
- No contiene lógica compleja
- Maneja autorización con @PreAuthorize

### 5. **Global Exception Handler**
- Todas las excepciones se centralizan
- Respuestas HTTP consistentes
- Nunca exponemos stacktraces internos

---

## Autenticación y Autorización

### JWT (JSON Web Tokens)

Cuando haces login:

```
POST /api/auth/login
{
  "email": "juan@sged.cl",
  "password": "secret123"
}
```

El servidor responde:

```
{
  "token": "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9...",
  "email": "juan@sged.cl",
  "nombre": "Juan",
  "rol": "ESTUDIANTE"
}
```

Luego, en cada petición, el frontend añade el token al header:

```
GET /api/cursos
Authorization: Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9...
```

El `JwtAuthFilter` valida el token. Si es válido, la petición pasa. Si no, rechaza con 401.

### Roles y Permisos

Cada usuario tiene un **rol**:

- `ADMINISTRADOR` — Acceso total
- `DOCENTE` — Gestiona cursos, calificaciones, asistencias
- `ESTUDIANTE` — Ve sus propias notas y cursos
- `INSPECTOR` — Supervisa (futuro)
- `APODERADO` — Ve datos de su hijo (futuro)

En los Controllers, usamos `@PreAuthorize` para controlar quién accede a qué:

```java
@PostMapping
@PreAuthorize("hasRole('DOCENTE') or hasRole('ADMINISTRADOR')")
public ResponseEntity<Calificacion> crear(@RequestBody Calificacion cal) {
    // Solo DOCENTE y ADMINISTRADOR pueden llegar aquí
}
```

---

## Base de Datos

### Tablas Principales

```sql
-- Usuarios
CREATE TABLE usuarios (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  nombre VARCHAR(100),
  apellido VARCHAR(100),
  email VARCHAR(100) UNIQUE,
  password VARCHAR(255),
  rol ENUM('DOCENTE', 'ESTUDIANTE', ...),
  activo BOOLEAN
);

-- Cursos
CREATE TABLE cursos (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  nombre VARCHAR(50) UNIQUE,
  nivel VARCHAR(20),
  letra VARCHAR(5),
  docente_jefe_id BIGINT,
  anio INT,
  activo BOOLEAN
);

-- Calificaciones
CREATE TABLE calificaciones (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  estudiante_id BIGINT,
  docente_id BIGINT,
  curso_id BIGINT,
  asignatura VARCHAR(100),
  tipo_evaluacion VARCHAR(50),
  nota DECIMAL(3,2),
  fecha DATE,
  observaciones TEXT
);

-- Asistencias
CREATE TABLE asistencias (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  estudiante_id BIGINT,
  curso_id BIGINT,
  fecha DATE,
  presente BOOLEAN,
  razon VARCHAR(200)
);
```

---

## Endpoints de la API

### Autenticación
- `POST /api/auth/login` — Login
- `POST /api/auth/register` — Registro

### Usuarios
- `GET /api/usuarios` — Listar todos
- `GET /api/usuarios/{id}` — Obtener uno
- `POST /api/usuarios` — Crear
- `PUT /api/usuarios/{id}` — Actualizar
- `DELETE /api/usuarios/{id}` — Desactivar

### Cursos
- `GET /api/cursos` — Listar
- `GET /api/cursos/activos` — Solo activos
- `GET /api/cursos/{id}` — Obtener
- `POST /api/cursos` — Crear (DOCENTE)
- `PUT /api/cursos/{id}` — Actualizar (DOCENTE)
- `DELETE /api/cursos/{id}` — Desactivar (ADMIN)

### Calificaciones
- `GET /api/calificaciones` — Listar todas
- `GET /api/calificaciones/estudiante/{id}` — Por estudiante
- `GET /api/calificaciones/curso/{id}` — Por curso
- `GET /api/calificaciones/estudiante/{id}/curso/{id}/promedio` — Promedio
- `POST /api/calificaciones` — Crear (DOCENTE)
- `PUT /api/calificaciones/{id}` — Actualizar (DOCENTE)
- `DELETE /api/calificaciones/{id}` — Eliminar (ADMIN)

### Asistencias
- Similares a calificaciones

### Dashboard
- `GET /api/dashboard/estadisticas-curso/{id}` — Estadísticas de un curso
- `GET /api/dashboard/desempeno-curso/{id}` — Desempeño de estudiantes
- `GET /api/dashboard/alertas-bienestar/{id}` — Alertas de bienestar
- `GET /api/dashboard/reporte-docente/{id}` — Reporte por docente

---

## Configuración Importante

### `application.properties`

```properties
# BD
spring.datasource.url=jdbc:mysql://localhost:3306/sged_db
spring.datasource.username=root
spring.datasource.password=

# JWT
jwt.secret=tu-clave-secreta-super-segura-cambiar-en-produccion
jwt.expiration=86400000  # 24 horas

# CORS (Frontend)
# Configurado en CorsConfig.java
```

---

## Patrones de Diseño Aplicados

1. **Monolito Modular** — Fácil de entender, desplegar, testear
2. **Separación de Capas** — Controller → Service → Repository
3. **DTOs** — Separa entrada/salida de entidades
4. **Repository Pattern** — Abstrae acceso a datos
5. **JWT** — Autenticación stateless
6. **Global Exception Handler** — Errores consistentes
7. **CORS** — Comunicación segura frontend-backend

---

## ¿Cómo Agregar un Nuevo Módulo?

1. Crear carpeta `nuevo_modulo/`
2. Crear `entity/Entidad.java`
3. Crear `repository/EntidadRepository.java`
4. Crear `service/EntidadService.java`
5. Crear `controller/EntidadController.java`
6. Crear `dto/EntidadDTO.java` (si necesario)

¡Listo! Ya está integrado en el sistema.

