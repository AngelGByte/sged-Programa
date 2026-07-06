# Guía de Instalación e Instrucciones — SGED

## Requisitos Previos

- **Java 25** o superior
- **Node.js 20+** y npm
- **MySQL 8.0+**
- **Git**
- **VS Code** con extensiones recomendadas

---

## Instalación Completa

### 1. Clonar el Repositorio

```bash
git clone <tu-repo>
cd sged-monolito
```

### 2. Configurar la Base de Datos

```bash
# Crear base de datos
mysql -u root -p << EOF
CREATE DATABASE sged_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
EOF

# Luego, el backend crea las tablas automáticamente (spring.jpa.hibernate.ddl-auto=update)
```

### 3. Configurar Backend

```bash
cd sged-backend

# Editar application.properties
# - Cambiar datasource.url si es necesario
# - Cambiar jwt.secret a una clave segura

# Compilar
./mvnw clean install

# O si prefieres skipear tests:
./mvnw clean install -DskipTests
```

### 4. Configurar Frontend

```bash
cd ../sged-frontend

# Instalar dependencias
npm install

# Verificar que package-lock.json se haya creado
```

---

## Ejecutar en Desarrollo

### Terminal 1: Backend

```bash
cd sged-backend

# Opción 1: Maven
./mvnw spring-boot:run

# Opción 2: Java directo (después de compilar)
java -jar target/sged-backend-1.0.0.jar
```

Backend estará en: `http://localhost:8080`

### Terminal 2: Frontend

```bash
cd sged-frontend

npm run dev
```

Frontend estará en: `http://localhost:5173`

---

## Credenciales de Prueba

El sistema carga usuarios iniciales automáticamente:

| Email | Contraseña | Rol |
|---|---|---|
| admin@sged.cl | admin123 | ADMINISTRADOR |
| carlos.mendoza@sged.cl | docente123 | DOCENTE |
| juan.gonzalez@sged.cl | estudiante123 | ESTUDIANTE |

---

## Endpoints Principales

### Autenticación
- `POST /api/auth/login` — Login
- `POST /api/auth/register` — Registro

### Usuarios
- `GET /api/usuarios` — Listar
- `GET /api/usuarios/{id}` — Obtener uno
- `POST /api/usuarios` — Crear
- `PUT /api/usuarios/{id}` — Actualizar
- `DELETE /api/usuarios/{id}` — Desactivar

### Cursos
- `GET /api/cursos` — Listar
- `POST /api/cursos` — Crear (DOCENTE)

### Calificaciones
- `GET /api/calificaciones` — Listar
- `GET /api/calificaciones/estudiante/{id}` — Por estudiante
- `POST /api/calificaciones` — Crear (DOCENTE)

### Asistencias
- Similar a calificaciones

### Dashboard
- `GET /api/dashboard/estadisticas-curso/{id}` — Estadísticas
- `GET /api/dashboard/reporte-docente/{id}` — Reporte por docente

---

## Estructura Base de Datos

Se crean automáticamente al ejecutar el backend:

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

## Troubleshooting

### Error: Connection refused (Backend no levanta)

```bash
# Verificar que MySQL está corriendo
# En Windows:
net start MySQL80

# En Linux/Mac:
brew services start mysql

# Verificar puerto 8080
lsof -i :8080
```

### Error: npm dependencies

```bash
cd sged-frontend
rm -rf node_modules package-lock.json
npm install
```

### Error: Port already in use

```bash
# Cambiar puerto en vite.config.js
# O matar proceso:
lsof -i :5173
kill -9 <PID>
```

### JWT Token expirado

El token tiene duración de 24 horas. Para cambiar:

Editar `application.properties`:

```properties
jwt.expiration=86400000  # milisegundos (24 hrs)
```

---

## Build para Producción

### Backend

```bash
cd sged-backend

# Build jar
./mvnw clean package

# Ejecutar jar
java -jar target/sged-backend-1.0.0.jar
```

### Frontend

```bash
cd sged-frontend

# Build
npm run build

# Resultado: carpeta dist/ lista para servir con nginx/apache
```

---

## Próximos Pasos (Mejoras Futuras)

- [ ] Agregar más módulos (Tutoría, Bienestar)
- [ ] Implementar paginación en listados
- [ ] Tests unitarios e integración
- [ ] Autenticación por dos factores
- [ ] Exportación a Excel/PDF
- [ ] Notificaciones por email
- [ ] Gráficos interactivos en dashboard
- [ ] Dark mode
