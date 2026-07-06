# Plan de Trabajo Futuro - SGED

## 📌 Estado Actual

✅ **Backend completo:** Spring Boot monolítico con 5 módulos, seguridad JWT, validación, DB schema.  
✅ **Frontend foundation:** React + Vite configurado, contexto autenticación, servicios HTTP, páginas base.  
✅ **Documentación:** Arquitectura, instalación, git flow.  

---

## 🔧 Próximas Fases

### Fase 1: Completar Frontend (ALTA PRIORIDAD)

#### 1.1 Páginas Completas

- [ ] **Login.jsx**
  - [x] Estructura básica
  - [ ] Validación de email
  - [ ] Manejo de errores
  - [ ] Loading states
  - [ ] Estilos responsivos

- [ ] **Register.jsx**
  - [x] Estructura básica
  - [ ] Validación de formulario
  - [ ] Contraseñas coincidentes
  - [ ] Email único
  - [ ] Success/error messages

- [ ] **Dashboard.jsx**
  - [x] Layout base
  - [ ] Bienvenida personalizada
  - [ ] Cards con estadísticas
  - [ ] Gráficos de desempeño
  - [ ] Últimas calificaciones
  - [ ] Alertas importantes

- [ ] **Cursos.jsx**
  - [ ] Lista de cursos
  - [ ] Crear curso (solo docentes)
  - [ ] Editar curso
  - [ ] Eliminar curso
  - [ ] Filtros y búsqueda
  - [ ] Paginación

- [ ] **Calificaciones.jsx**
  - [ ] Vista docente: Crear/editar notas
  - [ ] Vista estudiante: Ver notas
  - [ ] Tabla de calificaciones
  - [ ] Filtros por curso/asignatura
  - [ ] Cálculo de promedio visible
  - [ ] Exportar a Excel

- [ ] **Asistencias.jsx**
  - [ ] Registro de asistencias (docente)
  - [ ] Vista de asistencias (estudiante)
  - [ ] Gráfico de porcentaje
  - [ ] Búsqueda por fecha
  - [ ] Historial de ausencias

- [ ] **AdminPanel.jsx**
  - [ ] Gestión de usuarios
  - [ ] Crear/editar/eliminar usuarios
  - [ ] Asignar roles
  - [ ] Desactivar cuentas
  - [ ] Reporte de actividad

#### 1.2 Componentes Reutilizables

- [ ] **Navbar.jsx** — Barra de navegación
- [ ] **Sidebar.jsx** — Panel lateral con menú
- [ ] **Table.jsx** — Tabla genérica reutilizable
- [ ] **Modal.jsx** — Modal genérico
- [ ] **Button.jsx** — Button con variantes
- [ ] **Card.jsx** — Card component
- [ ] **FormInput.jsx** — Input reutilizable
- [ ] **Loading.jsx** — Spinner/skeleton
- [ ] **Badge.jsx** — Badge para estados

#### 1.3 Estilos Completos

- [ ] **layout.scss** — Estructura general (navbar, sidebar, main)
- [ ] **dashboard.scss** — Estilos dashboard
- [ ] **tables.scss** — Tablas
- [ ] **forms.scss** — Formularios
- [ ] **buttons.scss** — Botones
- [ ] **cards.scss** — Cards
- [ ] **_variables.scss** — Colores, tamaños (actualizar)
- [ ] **_mixins.scss** — Funciones reutilizables
- [ ] **responsive.scss** — Media queries

---

### Fase 2: Base de Datos (MEDIA PRIORIDAD)

#### 2.1 Script SQL Inicial

- [ ] Crear archivo `database-setup.sql`

```sql
CREATE DATABASE IF NOT EXISTS sged_db;
USE sged_db;

-- Tabla usuarios
CREATE TABLE usuarios (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  apellido VARCHAR(100) NOT NULL,
  email VARCHAR(100) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  rol ENUM('ADMINISTRADOR','DOCENTE','ESTUDIANTE','INSPECTOR','APODERADO'),
  activo BOOLEAN DEFAULT true,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX idx_email (email),
  INDEX idx_rol (rol)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla cursos
CREATE TABLE cursos (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(50) UNIQUE NOT NULL,
  nivel VARCHAR(20),
  letra VARCHAR(5),
  docente_jefe_id BIGINT,
  anio INT,
  activo BOOLEAN DEFAULT true,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (docente_jefe_id) REFERENCES usuarios(id),
  INDEX idx_anio (anio),
  INDEX idx_activo (activo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla calificaciones
CREATE TABLE calificaciones (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  estudiante_id BIGINT NOT NULL,
  docente_id BIGINT,
  curso_id BIGINT NOT NULL,
  asignatura VARCHAR(100),
  tipo_evaluacion VARCHAR(50),
  nota DECIMAL(3,2) CHECK (nota >= 1.0 AND nota <= 7.0),
  fecha DATE,
  observaciones TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (estudiante_id) REFERENCES usuarios(id),
  FOREIGN KEY (docente_id) REFERENCES usuarios(id),
  FOREIGN KEY (curso_id) REFERENCES cursos(id),
  INDEX idx_estudiante_curso (estudiante_id, curso_id),
  INDEX idx_fecha (fecha)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla asistencias
CREATE TABLE asistencias (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  estudiante_id BIGINT NOT NULL,
  curso_id BIGINT NOT NULL,
  fecha DATE NOT NULL,
  presente BOOLEAN,
  razon VARCHAR(200),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (estudiante_id) REFERENCES usuarios(id),
  FOREIGN KEY (curso_id) REFERENCES cursos(id),
  UNIQUE KEY unique_asistencia (estudiante_id, curso_id, fecha),
  INDEX idx_estudiante (estudiante_id),
  INDEX idx_fecha (fecha)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Datos iniciales
INSERT INTO usuarios (nombre, apellido, email, password, rol) VALUES
('Admin', 'Sistema', 'admin@sged.cl', '$2a$10$...', 'ADMINISTRADOR'),
('Carlos', 'Mendoza', 'carlos.mendoza@sged.cl', '$2a$10$...', 'DOCENTE'),
('Juan', 'González', 'juan.gonzalez@sged.cl', '$2a$10$...', 'ESTUDIANTE');
```

#### 2.2 Migración Automática

- [x] Ya configurado: `spring.jpa.hibernate.ddl-auto=update`
- [ ] Cambiar a `validate` en producción

---

### Fase 3: Testing (MEDIA PRIORIDAD)

#### 3.1 Tests Unitarios Backend

- [ ] **UsuarioServiceTest.java**
  - [ ] test_crearUsuario_exitoso
  - [ ] test_crearUsuario_emailDuplicado
  - [ ] test_obtenerPorId_existe
  - [ ] test_obtenerPorId_noExiste

- [ ] **CursoServiceTest.java**
  - [ ] test_crearCurso_exitoso
  - [ ] test_obtenerPorDocente
  - [ ] test_desactivarCurso

- [ ] **CalificacionServiceTest.java**
  - [ ] test_calcularPromedio
  - [ ] test_validarRanga1a7

- [ ] **AsistenciaServiceTest.java**
  - [ ] test_calcularPorcentaje

#### 3.2 Tests Unitarios Frontend

- [ ] **Login.test.jsx**
  - [ ] Renderiza formulario
  - [ ] Valida email requerido
  - [ ] Envía credenciales

- [ ] **AuthContext.test.jsx**
  - [ ] login() guarda token
  - [ ] logout() limpia storage
  - [ ] isAuthenticated actualiza

- [ ] **useAuth.test.js**
  - [ ] Hook accede a contexto
  - [ ] Lanza error sin provider

#### 3.3 Tests Integración

- [ ] **E2E Login Flow**
  - [ ] Usuario login → token → dashboard
  
- [ ] **E2E Crear Curso**
  - [ ] Docente crea curso → aparece en lista

- [ ] **E2E Calificar Estudiante**
  - [ ] Docente ingresa nota → estudiante la ve

---

### Fase 4: Mejoras y Features (BAJA PRIORIDAD)

#### 4.1 Paginación

- [ ] Backend: Implementar `PageRequest` en repositories
- [ ] Frontend: Componente `Pagination.jsx`
- [ ] Listar con 10-20 items por página

#### 4.2 Búsqueda y Filtros

- [ ] Backend: Agregar endpoints de búsqueda
- [ ] Frontend: Componentes `SearchBar.jsx`, `FilterSelect.jsx`
- [ ] Filtrar por: nombre, rol, curso, fecha

#### 4.3 Exportación

- [ ] Dependencia: `apache-poi` (Excel), `itextpdf` (PDF)
- [ ] Backend: Endpoints `/api/*/export`
- [ ] Frontend: Botones "Descargar Excel", "Descargar PDF"

#### 4.4 Notificaciones por Email

- [ ] Dependencia: `spring-boot-starter-mail`
- [ ] Configurar SMTP
- [ ] Enviar email al registrarse
- [ ] Notificaciones de calificaciones

#### 4.5 Autenticación 2FA

- [ ] Dependencia: `google-authenticator`
- [ ] QR code en registro
- [ ] Verificación de TOTP en login

#### 4.6 Gráficos Interactivos

- [ ] Ya instalado: Recharts
- [ ] Dashboard: Gráfico de desempeño por curso
- [ ] Gráfico de asistencias por mes
- [ ] Comparativa de notas

#### 4.7 Dark Mode

- [ ] CSS variables en SCSS
- [ ] Contexto para theme
- [ ] Toggle en navbar

#### 4.8 Internacionalización (i18n)

- [ ] Dependencia: `i18next`
- [ ] Soportar español/inglés
- [ ] Traducir componentes principales

---

## 📋 Checklist de Desarrollo

### Antes de Cada Commit

- [ ] ¿El código funciona?
- [ ] ¿Hay console.logs?
- [ ] ¿Está bien indentado?
- [ ] ¿Sigue las convenciones?
- [ ] ¿Hay comentarios útiles?

### Antes de PR (Pull Request)

- [ ] ¿Está documentado?
- [ ] ¿Hay tests?
- [ ] ¿No rompe nada existente?
- [ ] ¿Se probó en el navegador?

### Antes de Deploy

- [ ] ¿Pasan todos los tests?
- [ ] ¿Frontend compila sin warnings?
- [ ] ¿Backend compila sin errores?
- [ ] ¿Se probó en producción?

---

## 🚀 Roadmap Temporal

### Semana 1-2: Frontend Básico
- Completar Login, Register, Dashboard
- Navbar y Sidebar
- Estilos responsive

### Semana 3-4: Módulos Frontend
- Cursos (listar, crear, editar)
- Calificaciones (vista docente/estudiante)
- Asistencias (registro)

### Semana 5: Testing & Polish
- Tests unitarios e integración
- Bug fixes
- Optimización de performance

### Semana 6: Features Avanzadas
- Paginación
- Búsqueda/filtros
- Exportación Excel

### Semana 7-8: Final
- Testing completo
- Documentación
- Deploy

---

## 📚 Recursos Útiles

### React
- [React Docs](https://react.dev)
- [React Router Docs](https://reactrouter.com)
- [Axios Docs](https://axios-http.com)

### Spring Boot
- [Spring Boot Docs](https://spring.io/projects/spring-boot)
- [Spring Security](https://spring.io/projects/spring-security)
- [JPA/Hibernate](https://spring.io/projects/spring-data-jpa)

### Testing
- [Jest](https://jestjs.io) (Frontend)
- [JUnit 5](https://junit.org/junit5/) (Backend)
- [Testcontainers](https://www.testcontainers.org) (Integration)

---

## 📞 Contacto y Soporte

Si hay dudas sobre la arquitectura o cómo implementar algo:

1. Revisar documentación: `/markdown-backend/` y `/markdown-frontend/`
2. Consultar Git history: `git log --oneline`
3. Preguntar en el equipo

---

**Última actualización:** 2024  
**Próximo revisor:** Equipo de desarrollo
