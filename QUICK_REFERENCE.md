# Quick Reference - SGED

## 🚀 Iniciar el Proyecto

```bash
# Terminal 1: Backend
cd sged-monolito/sged-backend
mvn spring-boot:run
# Backend en http://localhost:8080

# Terminal 2: Frontend
cd sged-monolito/sged-frontend
npm install  # primera vez
npm run dev
# Frontend en http://localhost:5173
```

---

## 🔐 Credenciales de Prueba

```
Email: admin@sged.cl
Password: admin123
Role: ADMINISTRADOR

Email: carlos.mendoza@sged.cl
Password: docente123
Role: DOCENTE

Email: juan.gonzalez@sged.cl
Password: estudiante123
Role: ESTUDIANTE
```

---

## 📁 Estructura Rápida

```
sged-monolito/
├── sged-backend/
│   ├── pom.xml
│   ├── src/main/java/cl/duocuc/sged/
│   │   ├── config/ (JWT, CORS, Security)
│   │   ├── usuario/ (Login, Register, CRUD)
│   │   ├── curso/
│   │   ├── calificacion/
│   │   ├── asistencia/
│   │   └── dashboard/
│   └── src/main/resources/
│       └── application.properties
│
├── sged-frontend/
│   ├── package.json
│   ├── vite.config.js
│   ├── src/
│   │   ├── main.jsx
│   │   ├── App.jsx
│   │   ├── context/ (AuthContext)
│   │   ├── hooks/ (useAuth)
│   │   ├── services/ (axios, endpoints)
│   │   ├── components/ (PrivateRoute)
│   │   ├── pages/ (Login, Dashboard, etc)
│   │   └── styles/ (SCSS)
│   └── markdown-frontend/
│       └── 01-arquitectura-general.md
│
├── README.md
├── GUIA_INSTALACION.md
├── ESTRATEGIA_GIT.md
├── RESUMEN_ARQUITECTURA.md
└── PLAN_FUTURO.md
```

---

## 🌐 API Endpoints

### Autenticación
```
POST /api/auth/login
  Body: { email, password }
  Response: { token, nombre, apellido, rol }

POST /api/auth/register
  Body: { nombre, apellido, email, password }
```

### Usuarios
```
GET /api/usuarios                      # Listar
GET /api/usuarios/{id}                 # Obtener uno
POST /api/usuarios                     # Crear (ADMIN)
PUT /api/usuarios/{id}                 # Actualizar
DELETE /api/usuarios/{id}              # Desactivar
```

### Cursos
```
GET /api/cursos                        # Listar
GET /api/cursos/activos                # Solo activos
GET /api/cursos/{id}                   # Obtener
GET /api/cursos/docente/{id}           # Por docente
POST /api/cursos                       # Crear (DOCENTE)
PUT /api/cursos/{id}                   # Actualizar
DELETE /api/cursos/{id}                # Desactivar
```

### Calificaciones
```
GET /api/calificaciones                # Listar
GET /api/calificaciones/{id}
GET /api/calificaciones/estudiante/{id}
GET /api/calificaciones/curso/{id}
GET /api/calificaciones/estudiante/{id}/curso/{id}/promedio
POST /api/calificaciones               # Crear (DOCENTE)
PUT /api/calificaciones/{id}
DELETE /api/calificaciones/{id}
```

### Asistencias
```
GET /api/asistencias
GET /api/asistencias/estudiante/{id}
GET /api/asistencias/curso/{id}
GET /api/asistencias/estudiante/{id}/curso/{id}/porcentaje
POST /api/asistencias
PUT /api/asistencias/{id}
DELETE /api/asistencias/{id}
```

### Dashboard
```
GET /api/dashboard/estadisticas-curso/{id}
GET /api/dashboard/desempeno-curso/{id}
GET /api/dashboard/alertas-bienestar/{id}
GET /api/dashboard/reporte-docente/{id}
```

---

## 🔧 Comandos Útiles

### Maven (Backend)
```bash
# Compilar
mvn clean install

# Ejecutar
mvn spring-boot:run

# Tests
mvn test

# Build JAR
mvn clean package

# Skipear tests
mvn clean install -DskipTests
```

### NPM (Frontend)
```bash
# Instalar dependencias
npm install

# Desarrollo
npm run dev

# Build producción
npm run build

# Preview build
npm run preview

# Linting
npm run lint
```

### Git
```bash
# Crear feature branch
git checkout develop
git checkout -b feature/nombre-feature

# Commit
git add .
git commit -m "feat: descripción"

# Push
git push origin feature/nombre-feature

# Ver cambios
git diff
git log --oneline
```

---

## 🎨 Estructura Componentes React

```jsx
// Importar contexto
import { useAuth } from './hooks/useAuth'

// Componente con AuthContext
function MiComponente() {
  const { user, logout, isAuthenticated } = useAuth()
  
  return (
    {isAuthenticated ? (
      <>
        <p>Hola {user.nombre}</p>
        <button onClick={logout}>Logout</button>
      </>
    ) : (
      <p>No autenticado</p>
    )}
  )
}

// Rutas protegidas
<Route
  path="/dashboard"
  element={<PrivateRoute><Dashboard /></PrivateRoute>}
/>
```

---

## 💾 Servicios Frontend

```javascript
import { cursoService, calificacionService, authService } from './services'

// Auth
await authService.login('email', 'password')
await authService.register('nombre', 'apellido', 'email', 'password')

// Cursos
await cursoService.obtenerTodos()
await cursoService.obtenerPorId(1)
await cursoService.crear({ nombre: '1°A', ... })

// Calificaciones
await calificacionService.obtenerPorEstudiante(5)
await calificacionService.obtenerPromedio(5, 3)
await calificacionService.crear({ estudianteId: 5, ... })

// Asistencias
await asistenciaService.obtenerPorcentaje(5, 3)
await asistenciaService.crear({ ... })

// Dashboard
await dashboardService.obtenerEstadisticasCurso(3)
```

---

## 🐛 Troubleshooting Rápido

### Backend no levanta
```bash
# ¿MySQL está corriendo?
mysql -u root -p -e "SELECT 1"

# ¿Puerto 8080 ocupado?
lsof -i :8080

# ¿Error de compilación?
mvn clean compile
```

### Frontend no levanta
```bash
# ¿Node instalado?
node --version
npm --version

# ¿Puerto 5173 ocupado?
lsof -i :5173

# ¿Dependencias instaladas?
rm -rf node_modules package-lock.json
npm install
```

### JWT Token expirado
```
Token dura 24 horas.
Si expira, redirige automáticamente a /login
Cambiar duración en: application.properties → jwt.expiration
```

### CORS error
```
Si ves: "Access to XMLHttpRequest blocked by CORS policy"
1. Backend CORS config debe incluir tu URL frontend
2. Revisar CorsConfig.java
3. Permitir: http://localhost:5173
```

---

## 📝 Patrón de Código Backend

### Crear nuevo módulo (ej: Tutoría)

**1. Entity** (`Tutoria.java`)
```java
@Entity
@Table(name = "tutorias")
public class Tutoria {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @NotBlank
  private String tema;
  
  @ForeignKey // o Long docenteId
  private Long docenteId;
  // ... más campos
}
```

**2. Repository** (`TutoriaRepository.java`)
```java
public interface TutoriaRepository extends JpaRepository<Tutoria, Long> {
  List<Tutoria> findByDocenteId(Long docenteId);
}
```

**3. Service** (`TutoriaService.java`)
```java
@Service
public class TutoriaService {
  @Autowired
  private TutoriaRepository repo;
  
  public Tutoria obtenerPorId(Long id) {
    return repo.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("No existe"));
  }
  // ... más métodos
}
```

**4. Controller** (`TutoriaController.java`)
```java
@RestController
@RequestMapping("/api/tutorias")
@CrossOrigin(origins = "http://localhost:5173")
public class TutoriaController {
  @Autowired
  private TutoriaService service;
  
  @GetMapping
  public ResponseEntity<?> obtenerTodas() {
    return ResponseEntity.ok(service.obtenerTodas());
  }
  // ... más endpoints
}
```

**5. DTO** (`TutoriaDTO.java`)
```java
@Data
public class TutoriaDTO {
  @NotBlank
  private String tema;
  
  private Long docenteId;
}
```

---

## 📝 Patrón de Código Frontend

### Crear nueva página

**1. Servicio** (`tutoriaService.js`)
```javascript
export const tutoriaService = {
  obtenerTodas: () =>
    axiosConfig.get('/tutorias'),
  
  obtenerPorId: (id) =>
    axiosConfig.get(`/tutorias/${id}`),
  // ... más métodos
}
```

**2. Hook** (`useTutorias.js`) [opcional]
```javascript
export const useTutorias = () => {
  const [tutorias, setTutorias] = useState([])
  
  useEffect(() => {
    tutoriaService.obtenerTodas()
      .then(res => setTutorias(res.data))
      .catch(err => console.error(err))
  }, [])
  
  return { tutorias }
}
```

**3. Componente** (`Tutorias.jsx`)
```javascript
function Tutorias() {
  const { user } = useAuth()
  const [tutorias, setTutorias] = useState([])
  
  useEffect(() => {
    tutoriaService.obtenerTodas()
      .then(res => setTutorias(res.data))
      .catch(err => toast.error(err.message))
  }, [])
  
  return (
    <div className="page">
      <h1>Tutorías</h1>
      {tutorias.map(t => (
        <div key={t.id}>{t.tema}</div>
      ))}
    </div>
  )
}
```

**4. Ruta** (`App.jsx`)
```jsx
<Route
  path="/tutorias"
  element={<PrivateRoute><Tutorias /></PrivateRoute>}
/>
```

---

## 🎯 Roles y Permisos

```
ADMINISTRADOR
├── Ver todo
├── Crear/editar/eliminar usuarios
├── Ver reportes
└── Configurar sistema

DOCENTE
├── Crear cursos
├── Calificar estudiantes
├── Registrar asistencias
├── Ver reportes de sus cursos
└── NO puede ver otros docentes

ESTUDIANTE
├── Ver calificaciones propias
├── Ver asistencias propias
├── Ver cursos en los que está
└── NO puede calificar

INSPECTOR
├── Ver asistencias
├── Reportes de asistencia
└── Generar alertas

APODERADO
├── Ver datos de sus pupilos
├── Ver calificaciones
└── Ver asistencias
```

---

## 📊 Base de Datos Rápido

```sql
-- Usuarios con login
SELECT * FROM usuarios WHERE email = 'admin@sged.cl';

-- Cursos activos
SELECT * FROM cursos WHERE activo = true;

-- Notas de un estudiante
SELECT * FROM calificaciones WHERE estudiante_id = 1;

-- Promedio de un estudiante en un curso
SELECT AVG(nota) FROM calificaciones 
WHERE estudiante_id = 1 AND curso_id = 1;

-- Asistencias por mes
SELECT fecha, COUNT(*) as total FROM asistencias 
WHERE MONTH(fecha) = MONTH(NOW()) 
GROUP BY fecha;
```

---

## 🚀 Deploy Checklist

### Backend
- [ ] Cambiar JWT secret en production
- [ ] Cambiar database URL
- [ ] Cambiar spring.jpa.hibernate.ddl-auto a `validate`
- [ ] Remover logs de debug
- [ ] Build JAR: `mvn clean package`

### Frontend
- [ ] Cambiar API URL a backend production
- [ ] Remover console.logs
- [ ] Build: `npm run build`
- [ ] Servir carpeta `dist/`

---

**Este es un archivo de referencia rápida. Para información detallada, ver:**
- Backend: `/markdown-backend/01-arquitectura-general.md`
- Frontend: `/markdown-frontend/01-arquitectura-general.md`
- Instalación: `GUIA_INSTALACION.md`
