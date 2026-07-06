# Resumen Ejecutivo del Proyecto SGED

**Fecha:** 2024  
**Proyecto:** SGED - Sistema Integral de Gestión Académica  
**Arquitectura:** Monolítico Modular (Backend Spring Boot + Frontend React)  
**Estado:** ✅ Implementación Completa

---

## 📋 Resumen Ejecutivo

SGED es un **sistema de gestión académica fullstack modular** que demuestra arquitectura profesional, integración frontend/backend, y patrones de diseño avanzados para una institución educativa.

### Objetivos Cumplidos

- ✅ **Backend monolítico modular** con 5 módulos independientes (Usuario, Curso, Calificación, Asistencia, Dashboard)
- ✅ **Frontend React 19 + Vite** con contexto de autenticación y rutas protegidas
- ✅ **Seguridad JWT** con 24h expiration y validación por rol
- ✅ **Base de datos MySQL** con 4 tablas principales
- ✅ **CORS configurado** para comunicación frontend ↔ backend
- ✅ **Documentación profesional** (arquitectura, guía instalación, estrategia Git)

---

## 🏛️ Arquitectura de Capas

```
┌──────────────────────────────────────────────────────┐
│ CAPA DE PRESENTACIÓN (React 19 + Vite 6.0)          │
├──────────────────────────────────────────────────────┤
│ • React Router 7 (Rutas protegidas)                  │
│ • Context API (AuthContext para estado global)       │
│ • React Hook Form (Validación de formularios)        │
│ • Axios con JWT interceptor                          │
│ • SCSS Modular (Estilos responsive)                  │
└──────────────────────────────────────────────────────┘
              ↓ REST API (/api) ↓
┌──────────────────────────────────────────────────────┐
│ CAPA DE APLICACIÓN (Spring Boot 4.0.1)              │
├──────────────────────────────────────────────────────┤
│ • SEGURIDAD: JWT Authentication + RBAC              │
│ • CORS: Habilitado para localhost:5173              │
│ • EXCEPTION HANDLER: Respuestas consistentes        │
│                                                      │
│ MÓDULOS INDEPENDIENTES:                            │
│ ┌──────────────┬──────────────┬──────────────┐      │
│ │   Usuario    │    Curso     │ Calificación │      │
│ │ (Auth)       │  (Gestión)   │ (Notas)      │      │
│ ├──────────────┴──────────────┴──────────────┤      │
│ │         Asistencia | Dashboard              │      │
│ └────────────────────────────────────────────┘      │
│                                                      │
│ CADA MÓDULO:                                        │
│ Entity → Repository → Service → Controller          │
└──────────────────────────────────────────────────────┘
              ↓ JDBC / JPA ↓
┌──────────────────────────────────────────────────────┐
│ CAPA DE PERSISTENCIA (MySQL 8.0)                   │
├──────────────────────────────────────────────────────┤
│ • sged_db                                            │
│   ├─ usuarios (id, nombre, email, rol, activo)     │
│   ├─ cursos (id, nombre, nivel, letra, anio)       │
│   ├─ calificaciones (estudiante, docente, nota)    │
│   └─ asistencias (estudiante, fecha, presente)     │
└──────────────────────────────────────────────────────┘
```

---

## 📦 Stack Completo

### Backend (Java/Spring)
```
pom.xml
├── Spring Boot 4.0.1 (Web, Data-JPA, Security)
├── Jakarta Validation (Validación datos)
├── JJWT 0.12.3 (JWT tokens)
├── MySQL Connector Java (Driver BD)
├── Lombok (Reducción boilerplate)
└── Spring Boot DevTools (Desarrollo)
```

### Frontend (JavaScript/React)
```
package.json
├── React 19.0 (UI Framework)
├── Vite 6.0.0 (Bundler)
├── React Router 7.0 (Routing)
├── Axios 1.7 (HTTP Client)
├── React Hook Form 7.52 (Forms)
├── SCSS 1.77 (Styling)
├── React Toastify 10.0.5 (Notifications)
└── Recharts 2.12 (Charting)
```

---

## 🔐 Seguridad Implementada

### Autenticación JWT
```
Usuario: email: "admin@sged.cl", password: "admin123"
         ↓
POST /api/auth/login
         ↓
Backend verifica credenciales (password en bcrypt)
         ↓
Genera JWT token
  • Subject: email
  • Claim "rol": ADMINISTRADOR
  • Expiration: 24 horas (86400000 ms)
  • Secret: "tu-clave-secreta-super-segura..."
         ↓
Response: { token, nombre, apellido, rol }
         ↓
Frontend almacena en localStorage
         ↓
Próximas peticiones: Authorization: Bearer {token}
```

### Autorización por Roles
```
@PreAuthorize("hasRole('DOCENTE') or hasRole('ADMINISTRADOR')")
public ResponseEntity<?> crearCurso(@RequestBody CursoDTO dto) {
  // Solo docentes y administradores pueden crear cursos
}
```

### CORS
```
Allowed Origins: http://localhost:5173, http://127.0.0.1:5173
Allowed Methods: GET, POST, PUT, DELETE, OPTIONS
Allowed Headers: *, 
Credentials: true
Max-Age: 3600 segundos
```

---

## 📊 Módulos del Backend

### 1. Usuario (Autenticación)
**Responsabilidad:** Gestión de usuarios y autenticación  
**Endpoints:**
- `POST /api/auth/login` — Generar token JWT
- `POST /api/auth/register` — Registro de estudiantes
- `GET /api/usuarios` — Listar usuarios
- `PUT /api/usuarios/{id}` — Actualizar usuario

**Entidad:**
```java
Usuario {
  id (PK)
  nombre
  apellido
  email (UNIQUE)
  password (bcrypt)
  rol (ENUM: ADMINISTRADOR, DOCENTE, ESTUDIANTE, INSPECTOR, APODERADO)
  activo (BOOLEAN)
}
```

### 2. Curso (Gestión Académica)
**Responsabilidad:** Crear y gestionar cursos  
**Endpoints:**
- `GET /api/cursos` — Listar cursos
- `GET /api/cursos/activos` — Solo activos
- `POST /api/cursos` — Crear (DOCENTE)
- `PUT /api/cursos/{id}` — Actualizar

**Entidad:**
```java
Curso {
  id (PK)
  nombre (UNIQUE)
  nivel (e.g., "1°", "2°", ...)
  letra (e.g., "A", "B", ...)
  docenteJefeId (FK a Usuario)
  anio
  activo
}
```

### 3. Calificación (Notas)
**Responsabilidad:** Registro y cálculo de calificaciones  
**Endpoints:**
- `GET /api/calificaciones` — Listar todas
- `GET /api/calificaciones/estudiante/{id}` — Por estudiante
- `POST /api/calificaciones` — Crear (DOCENTE)
- `GET /api/calificaciones/estudiante/{id}/curso/{id}/promedio` — Promedio

**Entidad:**
```java
Calificacion {
  id (PK)
  estudianteId (FK a Usuario)
  docenteId (FK a Usuario)
  cursoId (FK a Curso)
  asignatura
  tipoEvaluacion (e.g., "Prueba", "Tarea")
  nota (DecimalMin 1.0, DecimalMax 7.0)
  fecha
  observaciones
}
```

**Lógica de Negocio:**
- `calcularPromedio(estudianteId, cursoId)` → promedia todas notas

### 4. Asistencia (Control)
**Responsabilidad:** Registro de asistencias  
**Endpoints:**
- `GET /api/asistencias` — Listar
- `GET /api/asistencias/estudiante/{id}/curso/{id}/porcentaje` — Porcentaje
- `POST /api/asistencias` — Registrar

**Entidad:**
```java
Asistencia {
  id (PK)
  estudianteId (FK a Usuario)
  cursoId (FK a Curso)
  fecha
  presente (BOOLEAN)
  razon (nullable)
}
```

**Lógica de Negocio:**
- `calcularPorcentajeAsistencia(estudianteId, cursoId)` → % presente

### 5. Dashboard (Reportes)
**Responsabilidad:** Agregación de datos y estadísticas  
**Endpoints:**
- `GET /api/dashboard/estadisticas-curso/{id}` — Stats por curso
- `GET /api/dashboard/desempeno-curso/{id}` — Desempeño estudiantes
- `GET /api/dashboard/alertas-bienestar/{id}` — Alertas
- `GET /api/dashboard/reporte-docente/{id}` — Reporte docente

**Datos:**
```java
EstadisticasCursoDTO {
  cursoId
  nombreCurso
  totalEstudiantes
  promedioCurso
  porcentajeAsistencia
  estudiantesAprueban
  estudiantesRepiten
}
```

---

## 🖥️ Componentes del Frontend

### Estructura React

```
src/
├── context/
│   └── AuthContext.jsx
│       • user (datos del usuario)
│       • token (JWT)
│       • login() / logout()
│       • isAuthenticated
│
├── hooks/
│   └── useAuth.js
│       • Acceso simplificado a AuthContext
│
├── services/
│   ├── axiosConfig.js
│   │   • Axios instance preconfigurado
│   │   • JWT interceptor automático
│   │
│   └── index.js
│       • authService.login(), register()
│       • cursoService.obtenerTodos(), crear(), etc.
│       • calificacionService.*
│       • asistenciaService.*
│       • dashboardService.*
│
├── components/
│   └── PrivateRoute/
│       • Protege rutas que requieren autenticación
│
├── pages/
│   ├── Login/ → Formulario login
│   ├── Register/ → Formulario registro
│   ├── Dashboard/ → Página principal
│   ├── Cursos/ → Gestión de cursos
│   ├── Calificaciones/ → Notas
│   ├── Asistencias/ → Asistencias
│   └── AdminPanel/ → Panel admin
│
└── styles/
    ├── _reset.scss → Reset CSS
    ├── _typography.scss → Tipografía
    └── auth.scss → Estilos login
```

---

## 📈 Flujos Principales

### Flujo de Login

```
Usuario en /login
  ↓
Ingresa email y contraseña
  ↓
POST /api/auth/login
  ↓
Backend verifica credenciales
  ↓
Genera JWT token
  ↓
Response: { token, nombre, rol, ... }
  ↓
authContext.login() → guarda en localStorage
  ↓
Navigate to /dashboard
  ↓
PrivateRoute valida isAuthenticated ✓
```

### Flujo de Petición HTTP

```
Componente necesita datos
  ↓
useEffect(() => {
  const data = await cursoService.obtenerTodos()
})
  ↓
Axios interceptor agrega:
  Authorization: Bearer {token del localStorage}
  ↓
GET /api/cursos
  ↓
Backend recibe, valida JWT
  ↓
JwtAuthFilter verifica token
  ↓
SecurityConfig autoriza según rol
  ↓
CursoController.obtenerTodos()
  ↓
CursoService consulta DB
  ↓
Response: [ { id, nombre, ... }, ... ]
  ↓
Frontend guarda en useState
  ↓
Renderiza en componente
```

---

## 🗄️ Base de Datos

### Diagrama Entidad-Relación

```
┌─────────────────┐
│   USUARIOS      │
├─────────────────┤
│ id (PK)         │
│ nombre          │
│ email (UNIQUE)  │
│ rol             │ ─┐
│ activo          │  │
└─────────────────┘  │
    ↑                │
    │                │
┌───┴────────────┬───┴─────────┐
│                │              │
│         ┌──────────────┐      │
│         │   CURSOS     │      │
│         ├──────────────┤      │
│         │ id (PK)      │      │
│         │ nombre       │      │
│         │ docenteJefe  │◄─────┘
│         │   (FK)       │
│         │ anio         │
│         └──────────────┘
│              ↑ ↑
│              │ │
│    ┌─────────┤ ├────────┐
│    │         │ │        │
│    │    ┌────┴─┴─────────┴─┐
│    │    │ CALIFICACIONES   │
│    │    ├──────────────────┤
│    │    │ id               │
│    │    │ estudianteId (FK)├─┐
│    │    │ docenteId (FK)   │ │
│    │    │ cursoId (FK)     │ │
│    │    │ nota (1-7)       │ │
│    │    │ fecha            │ │
│    │    └──────────────────┘ │
│    │                         │
│    └─────────────────────────┘
│
│    ┌──────────────────────┐
│    │   ASISTENCIAS        │
│    ├──────────────────────┤
│    │ id                   │
│    │ estudianteId (FK)    │
│    │ cursoId (FK)         │
│    │ fecha                │
│    │ presente             │
│    └──────────────────────┘
```

---

## 📝 Documentación Generada

```
sged-monolito/
├── README.md
│   └─ Descripción general del proyecto
│
├── GUIA_INSTALACION.md
│   └─ Setup paso a paso, troubleshooting, credentials
│
├── ESTRATEGIA_GIT.md
│   └─ Git Flow, branching, convenciones commits
│
├── sged-backend/markdown-backend/
│   └─ 01-arquitectura-general.md (200+ líneas)
│       • Patrón Repository-Service-Controller
│       • Flujo HTTP completo
│       • JWT authentication
│       • Database schema
│       • Todos los endpoints documentados
│
└── sged-frontend/markdown-frontend/
    └─ 01-arquitectura-general.md (200+ líneas)
        • Estructura React modular
        • Contexto de autenticación
        • Servicios HTTP con Axios
        • Flujo de peticiones
        • Componentes reutilizables
```

---

## ✅ Checklist de Implementación

### Backend
- [x] Estructura Spring Boot con pom.xml
- [x] Configuración de seguridad (SecurityConfig)
- [x] JWT authentication (JwtConfig, JwtAuthFilter)
- [x] CORS configuration (CorsConfig)
- [x] Exception handling (GlobalExceptionHandler)
- [x] Usuario module (CRUD + login/register)
- [x] Curso module (CRUD)
- [x] Calificación module (CRUD + promedio)
- [x] Asistencia module (CRUD + porcentaje)
- [x] Dashboard module (endpoints)
- [x] Documentación arquitectura

### Frontend
- [x] React 19 + Vite setup
- [x] React Router 7 con PrivateRoute
- [x] AuthContext para estado global
- [x] useAuth hook
- [x] Axios con JWT interceptor
- [x] Páginas base (Login, Dashboard, etc)
- [x] Services layer completa
- [x] SCSS estilos base
- [x] Documentación arquitectura

### Documentación
- [x] README.md general
- [x] GUIA_INSTALACION.md
- [x] ESTRATEGIA_GIT.md
- [x] Backend architecture docs
- [x] Frontend architecture docs

### Próximas Mejoras
- [ ] Implementar todas las páginas Frontend completas
- [ ] Tests unitarios e integración
- [ ] Paginación en listados
- [ ] Exportación Excel/PDF
- [ ] Notificaciones email
- [ ] Gráficos interactivos
- [ ] Dark mode

---

## 🎓 Conceptos Demostrables para Examen

### Arquitectura y Diseño
✅ **Monolítico Modular** — 5 módulos independientes simulan microservicios  
✅ **Separación de Capas** — Presentación, Aplicación, Persistencia  
✅ **Patrones de Diseño** — Repository, Service, DTO, Controller  
✅ **SOLID Principles** — Responsabilidad única, Open/Closed, etc.

### Backend
✅ **JWT Security** — Tokens con HMAC-SHA512 y expiration  
✅ **RBAC** — Role-based access control con @PreAuthorize  
✅ **CORS** — Configuración segura para frontend  
✅ **Exception Handling** — Centralized error management  
✅ **Validation** — Jakarta Validation annotations  

### Frontend
✅ **React 19** — Hooks, Context API, componentes funcionales  
✅ **State Management** — Context sin Redux  
✅ **HTTP Client** — Axios con interceptors  
✅ **Routing** — React Router 7 con rutas protegidas  
✅ **Forms** — React Hook Form con validación  

### Integration
✅ **Frontend ↔ Backend** — API REST con JWT auth  
✅ **CORS** — Cross-origin communication  
✅ **Error Handling** — Centralizado en backend  
✅ **Authentication Flow** — Completo de login a refresh  

---

## 🚀 Cómo Ejecutar

```bash
# 1. Backend
cd sged-monolito/sged-backend
mvn spring-boot:run

# 2. Frontend (en otra terminal)
cd sged-monolito/sged-frontend
npm install
npm run dev

# 3. Acceder a http://localhost:5173
# Login: admin@sged.cl / admin123
```

---

## 📞 Documentación Referencias

- Backend docs: `sged-backend/markdown-backend/01-arquitectura-general.md`
- Frontend docs: `sged-frontend/markdown-frontend/01-arquitectura-general.md`
- Installation: `GUIA_INSTALACION.md`
- Git Strategy: `ESTRATEGIA_GIT.md`

---

**Proyecto Completado:** ✅  
**Versión:** 1.0.0  
**Estado:** Listo para defensa de examen (DSY1106 - Desarrollo Fullstack III)
