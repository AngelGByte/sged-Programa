# SGED - Sistema Integral de Gestión Académica

> **Fullstack Monolith Modular** — Backend Spring Boot + Frontend React/Vite

---

## 🎓 Descripción del Proyecto

**SGED** es un sistema de gestión académica completo diseñado para instituciones educativas. Permite a **docentes** calificar, registrar asistencias y gestionar cursos, mientras que los **estudiantes** pueden revisar sus calificaciones y asistencias en tiempo real.

### Temática Educativa
- 📚 Gestión de cursos
- 👨‍🎓 Administración de estudiantes
- ✍️ Registro de calificaciones
- 📋 Control de asistencias
- 📊 Reportes y estadísticas

---

## 🏗️ Arquitectura: Monolítico Modular

Este proyecto implementa un **monolito modular** que demuestra conceptos de arquitectura de microservicios sin la complejidad operacional:

```
┌─────────────────────────────────────┐
│         Spring Boot Backend         │
├─────────────────────────────────────┤
│  Modulo Usuario  │  Modulo Cursos   │
│  Modulo Calif.   │  Modulo Asist.   │
│  Modulo Dashboard                   │
├─────────────────────────────────────┤
│  Seguridad (JWT) │ CORS             │
│  Exception Handler                  │
└─────────────────────────────────────┘
           ↓ API REST ↓
┌─────────────────────────────────────┐
│      React 19 + Vite Frontend       │
├─────────────────────────────────────┤
│  AuthContext  │ Hooks & Services    │
│  React Router │ Components          │
│  SCSS Styling │ Forms & Validation  │
└─────────────────────────────────────┘
           ↓ MySQL Database ↓
┌─────────────────────────────────────┐
│   sged_db (4 tablas principales)    │
└─────────────────────────────────────┘
```

---

## ✨ Características

### Backend (Spring Boot 4.0.1 + Java 25)
- ✅ **Autenticación JWT** con 24h expiration
- ✅ **5 módulos independientes** con patrón Entity→Repository→Service→Controller
- ✅ **Control de acceso basado en roles** (ADMINISTRADOR, DOCENTE, ESTUDIANTE, etc.)
- ✅ **Manejo global de excepciones** con respuestas JSON consistentes
- ✅ **CORS configurado** para localhost:5173
- ✅ **Validación de datos** con Jakarta Validation
- ✅ **Cálculos de negocio** (promedio de calificaciones, porcentaje de asistencia)

### Frontend (React 19 + Vite 6.0.0)
- ✅ **Context API** para gestión de autenticación global
- ✅ **React Router 7** con rutas protegidas
- ✅ **Axios** con JWT interceptor automático
- ✅ **React Hook Form** para formularios validados
- ✅ **Estilos SCSS** modular y responsive
- ✅ **Toast notifications** con React Toastify
- ✅ **Gráficos** con Recharts

---

## 📦 Stack Tecnológico

| Componente | Versión | Propósito |
|---|---|---|
| Java | 25 | Lenguaje backend |
| Spring Boot | 4.0.1 | Framework backend |
| Maven | 4.0 | Build backend |
| MySQL | 8.0+ | Base de datos |
| React | 19.0 | UI framework |
| Vite | 6.0 | Build frontend |
| React Router | 7.0 | Routing |
| Axios | 1.7 | HTTP client |
| JJWT | 0.12.3 | JWT auth |
| SCSS | 1.77 | Estilos |

---

## 📁 Estructura del Proyecto

```
sged-monolito/
│
├── sged-backend/                      ← Backend Spring Boot
│   ├── pom.xml
│   ├── src/main/java/cl/duocuc/sged/
│   │   ├── SgedApplication.java
│   │   ├── config/
│   │   │   ├── SecurityConfig.java
│   │   │   ├── JwtConfig.java
│   │   │   ├── CorsConfig.java
│   │   │   └── JwtAuthFilter.java
│   │   ├── exception/
│   │   │   ├── ResourceNotFoundException.java
│   │   │   ├── BadRequestException.java
│   │   │   └── GlobalExceptionHandler.java
│   │   ├── usuario/
│   │   │   ├── Usuario.java (Entity)
│   │   │   ├── UsuarioRepository.java
│   │   │   ├── UsuarioService.java
│   │   │   ├── UsuarioController.java
│   │   │   ├── UsuarioDTO.java
│   │   │   └── AuthController.java
│   │   ├── curso/
│   │   │   ├── Curso.java
│   │   │   ├── CursoRepository.java
│   │   │   ├── CursoService.java
│   │   │   ├── CursoController.java
│   │   │   └── CursoDTO.java
│   │   ├── calificacion/
│   │   │   ├── Calificacion.java
│   │   │   ├── CalificacionRepository.java
│   │   │   ├── CalificacionService.java
│   │   │   ├── CalificacionController.java
│   │   │   └── CalificacionDTO.java
│   │   ├── asistencia/
│   │   │   ├── Asistencia.java
│   │   │   ├── AsistenciaRepository.java
│   │   │   ├── AsistenciaService.java
│   │   │   ├── AsistenciaController.java
│   │   │   └── AsistenciaDTO.java
│   │   └── dashboard/
│   │       ├── DashboardService.java
│   │       ├── DashboardController.java
│   │       └── DashboardDTO.java
│   └── src/main/resources/
│       └── application.properties
│
├── sged-frontend/                     ← Frontend React + Vite
│   ├── package.json
│   ├── vite.config.js
│   ├── index.html
│   ├── src/
│   │   ├── main.jsx
│   │   ├── App.jsx
│   │   ├── context/
│   │   │   └── AuthContext.jsx
│   │   ├── hooks/
│   │   │   └── useAuth.js
│   │   ├── services/
│   │   │   ├── axiosConfig.js
│   │   │   └── index.js
│   │   ├── components/
│   │   │   └── PrivateRoute/
│   │   ├── pages/
│   │   │   ├── Login/
│   │   │   ├── Register/
│   │   │   ├── Dashboard/
│   │   │   ├── Cursos/
│   │   │   ├── Calificaciones/
│   │   │   ├── Asistencias/
│   │   │   └── AdminPanel/
│   │   └── styles/
│   │       ├── _reset.scss
│   │       ├── _typography.scss
│   │       └── auth.scss
│   └── markdown-frontend/
│       └── 01-arquitectura-general.md
│
├── sged-backend/markdown-backend/     ← Documentación Backend
│   └── 01-arquitectura-general.md
│
├── GUIA_INSTALACION.md               ← Cómo instalar y ejecutar
├── ESTRATEGIA_GIT.md                 ← Git Flow y branching
└── README.md (este archivo)
```

---

## 🚀 Quickstart

### 1. Requisitos
- Java 25+
- Node.js 20+
- MySQL 8.0+

### 2. Instalación

**Backend:**
```bash
cd sged-monolito/sged-backend
mvn clean install
mvn spring-boot:run
```

**Frontend:**
```bash
cd sged-monolito/sged-frontend
npm install
npm run dev
```

### 3. Acceder

Frontend: `http://localhost:5173`
Backend: `http://localhost:8080`

### 4. Credenciales de Prueba

| Email | Password | Rol |
|---|---|---|
| admin@sged.cl | admin123 | ADMINISTRADOR |
| carlos.mendoza@sged.cl | docente123 | DOCENTE |
| juan.gonzalez@sged.cl | estudiante123 | ESTUDIANTE |

---

## 📖 Documentación

- 📘 **[Arquitectura Backend](sged-backend/markdown-backend/01-arquitectura-general.md)** — Patrones, API endpoints, seguridad
- 🎨 **[Arquitectura Frontend](sged-frontend/markdown-frontend/01-arquitectura-general.md)** — React, Context API, hooks, servicios
- 🔧 **[Guía de Instalación](GUIA_INSTALACION.md)** — Setup, troubleshooting, credentials
- 🌿 **[Estrategia Git](ESTRATEGIA_GIT.md)** — Git Flow, branching, commits

---

## 🎯 Patrones de Diseño Implementados

### Backend
1. **Repository Pattern** — Abstracción de datos
2. **Service Pattern** — Lógica de negocio centralizada
3. **DTO Pattern** — Separación de transferencia de datos
4. **Controller/Mesero Pattern** — Enrutamiento HTTP
5. **Singleton Pattern** — JWT Config, Security Config
6. **Strategy Pattern** — Múltiples estrategias de validación

### Frontend
1. **Context API** — Estado global sin Redux
2. **Custom Hooks** — Lógica reutilizable
3. **Axios Interceptors** — Inyección de JWT automática
4. **Router Lazy Loading** — Carga de rutas bajo demanda
5. **Module Pattern** — Servicios con métodos estáticos

---

## 🔐 Seguridad

### Autenticación
- JWT tokens con HMAC-SHA512
- Token expiration: 24 horas
- Stored en localStorage (frontend)
- Transmitido en Authorization header

### Autorización
- Role-based access control (RBAC)
- `@PreAuthorize` en endpoints sensibles
- Validación en SecurityConfig

### CORS
- Solo localhost:5173 permitido
- Credentials: true
- Max-Age: 3600 segundos

### Validación
- Jakarta Validation en DTOs
- GlobalExceptionHandler captura errores
- Responses JSON consistentes

---

## 📊 Base de Datos

**Auto-creadas por Hibernate (spring.jpa.hibernate.ddl-auto=update)**:

- **usuarios** — Usuarios del sistema
- **cursos** — Cursos y niveles
- **calificaciones** — Notas por estudiante
- **asistencias** — Registro de asistencia

---

## 🧪 Testing

Para futuros tests:

```bash
# Backend
mvn test

# Frontend
npm run test
```

---

## 📈 Próximas Mejoras

- [ ] Tests unitarios e integración
- [ ] Paginación en listados
- [ ] Exportación a Excel/PDF
- [ ] Notificaciones por email
- [ ] Autenticación por dos factores
- [ ] Gráficos interactivos mejorados
- [ ] Dark mode
- [ ] Internacionalización (i18n)

---

## 👥 Roles del Sistema

| Rol | Permisos |
|---|---|
| **ADMINISTRADOR** | Gestión de usuarios, reporte general |
| **DOCENTE** | Crear cursos, calificar, registrar asistencias |
| **ESTUDIANTE** | Ver calificaciones, asistencias propias |
| **INSPECTOR** | Supervisar asistencias |
| **APODERADO** | Ver información de estudiantes a su cargo |

---

## 🤝 Convenciones de Código

### Java
- Nombres de clases: `PascalCase` (UsuarioService)
- Métodos: `camelCase` (obtenerPorId)
- Constantes: `UPPER_SNAKE_CASE`

### JavaScript/React
- Componentes: `PascalCase` (Dashboard.jsx)
- Funciones: `camelCase` (useAuth)
- Variables: `camelCase`

### Git Commits
- `feat:` Nueva funcionalidad
- `fix:` Corrección
- `docs:` Documentación
- `refactor:` Reestructuración
- `test:` Tests

---

## 📞 Contacto y Soporte

Para preguntas o problemas:

1. Revisar documentación en `/markdown-backend/` y `/markdown-frontend/`
2. Verificar `GUIA_INSTALACION.md` troubleshooting
3. Consultar `ESTRATEGIA_GIT.md` para control de versiones

---

## 📝 Licencia

Proyecto educativo para DSY1106 - Desarrollo Fullstack III

---

**Última actualización:** 2024
**Versión:** 1.0.0
