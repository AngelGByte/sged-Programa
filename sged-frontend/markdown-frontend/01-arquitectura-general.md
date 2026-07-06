# Arquitectura General del Frontend — SGED

## ¿Qué es el Frontend?

El **frontend** es la interfaz visual que los usuarios ven en el navegador. Está construido con **React 19** y **Vite**, y se comunica con el backend a través de **API REST**.

---

## Estructura del Proyecto

```
sged-frontend/
│
├── package.json                    ← Dependencias y scripts
├── vite.config.js                  ← Configuración de Vite
├── index.html                      ← HTML principal
│
└── src/
    ├── main.jsx                    ← Punto de entrada
    ├── App.jsx                     ← Componente raíz (rutas)
    │
    ├── context/
    │   └── AuthContext.jsx         ← Estado global de autenticación
    │
    ├── hooks/
    │   └── useAuth.js              ← Hook para acceder al contexto
    │
    ├── services/
    │   ├── axiosConfig.js          ← Cliente HTTP configurado
    │   └── index.js                ← Servicios de cada módulo
    │
    ├── components/
    │   └── PrivateRoute/           ← Componente de protección de rutas
    │       └── PrivateRoute.jsx
    │
    ├── pages/
    │   ├── Login/
    │   ├── Register/
    │   ├── Dashboard/
    │   ├── Cursos/
    │   ├── Calificaciones/
    │   ├── Asistencias/
    │   └── AdminPanel/
    │
    ├── styles/
    │   ├── _reset.scss             ← Reset CSS
    │   ├── _typography.scss        ← Tipografía global
    │   └── auth.scss               ← Estilos de autenticación
    │
    └── validators/                 ← Funciones de validación (futuro)
```

---

## Conceptos Clave

### 1. **Contexto de Autenticación (AuthContext)**

Guarda el estado global del usuario autenticado:

```javascript
const { user, token, isAuthenticated, login, logout, userRole } = useAuth()
```

- `user` — Datos del usuario (nombre, email, rol)
- `token` — JWT token para peticiones autenticadas
- `isAuthenticated` — Boolean para saber si está logueado
- `login()` — Guarda usuario y token
- `logout()` — Limpia sesión
- `userRole` — Rol del usuario (para autorización)

### 2. **Hooks (useAuth)**

Custom hook que simplifica el acceso al contexto:

```javascript
import { useAuth } from './hooks/useAuth'

function MiComponente() {
  const { user, logout } = useAuth()
  return <p>Hola {user.nombre}</p>
}
```

### 3. **Servicios (Services)**

Capas que encapsulan llamadas HTTP al backend:

```javascript
import { cursoService, calificacionService } from './services'

const cursos = await cursoService.obtenerTodos()
const notas = await calificacionService.obtenerPorEstudiante(5)
```

Cada servicio:
- Centraliza las URLs de la API
- Gestiona parámetros y respuestas
- Reutilizable en múltiples componentes

### 4. **PrivateRoute**

Componente que protege rutas que requieren autenticación:

```javascript
<Route
  path="/dashboard"
  element={<PrivateRoute><Dashboard /></PrivateRoute>}
/>
```

Si no estás autenticado, redirige a `/login`.

### 5. **AxiosConfig**

Cliente HTTP preconfigurado que:
- Agrega automáticamente el token JWT a cada petición
- Maneja errores de autenticación (401)
- Redirige a login si el token expira

---

## Flujo de una Petición en el Frontend

### Ejemplo: Obtener cursos

```
Componente (Cursos.jsx)
  ↓
useEffect(() => {
  const data = await cursoService.obtenerTodos()
})
  ↓
AxiosConfig (Cliente HTTP)
  ↓
Agrega header: Authorization: Bearer {token}
  ↓
GET /api/cursos
  ↓
Backend responde
  ↓
Guardar en estado (useState)
  ↓
Renderizar en componente
```

---

## Patrones Aplicados

### 1. **Context API**
- Estado global sin necesidad de Redux
- Perfecto para autenticación

### 2. **Custom Hooks**
- Lógica reutilizable
- Separación de concerns

### 3. **Axios Interceptors**
- Inyectar JWT automáticamente
- Manejar 401 centralizadamente

### 4. **React Router v7**
- Navegación client-side
- PrivateRoute para protección
- Rutas dinámicas

### 5. **SCSS Modular**
- Variables y mixins
- Estilos por página/componente
- Reutilización de código

---

## Cómo Usar los Servicios

### Obtener datos

```javascript
import { cursoService } from './services'

async function cargarCursos() {
  try {
    const response = await cursoService.obtenerTodos()
    console.log(response.data)
  } catch (error) {
    console.error(error.response.data)
  }
}
```

### Crear datos

```javascript
async function crearCurso(cursoDTO) {
  try {
    const response = await cursoService.crear({
      nombre: '1°A',
      nivel: '1°',
      letra: 'A',
      docenteJefeId: 5,
      anio: 2024
    })
    toast.success('Curso creado!')
  } catch (error) {
    toast.error(error.response.data.message)
  }
}
```

### Actualizar datos

```javascript
async function actualizarCurso(id, datos) {
  const response = await cursoService.actualizar(id, datos)
  return response.data
}
```

---

## Autenticación: Flujo Completo

### 1. **Login**

```javascript
const response = await authService.login('juan@sged.cl', 'password123')
// Response: { token, email, nombre, apellido, rol }

login(userData, token)  // Guarda en contexto y localStorage
```

### 2. **Usar el token**

AxiosConfig agrega automáticamente a cada petición:

```
Authorization: Bearer eyJhbGc...
```

### 3. **Logout**

```javascript
logout()  // Limpia contexto y localStorage
navigate('/login')
```

### 4. **Renovación**

Si el token expira, el interceptor redirige a login:

```javascript
if (error.response?.status === 401) {
  localStorage.removeItem('token')
  window.location.href = '/login'
}
```

---

## Componentes Reutilizables (Próximos Pasos)

Para futuras mejoras, crear componentes reutilizables:

```
components/
├── Button/
├── Card/
├── Modal/
├── Form/
├── Table/
├── Navbar/
├── Footer/
└── PrivateRoute/
```

---

## Variables de Entorno (Próximas)

Crear `.env.local`:

```
VITE_API_URL=http://localhost:8080/api
VITE_APP_NAME=SGED
```

Usar en el código:

```javascript
const API_URL = import.meta.env.VITE_API_URL
```

---

## Testing (Próximo)

Con Vitest + React Testing Library:

```javascript
import { render, screen } from '@testing-library/react'
import Login from './pages/Login'

test('renderiza el formulario de login', () => {
  render(<Login />)
  expect(screen.getByLabelText(/email/i)).toBeInTheDocument()
})
```

---

## Deployment

### Build

```bash
npm run build
```

Genera carpeta `dist/` lista para producción.

### Servir

```bash
npm run preview
```

---

## Stack de Tecnologías

| Tech | Versión | Uso |
|---|---|---|
| React | 19.0 | Framework UI |
| React Router | 7.0 | Navegación |
| Vite | 6.0 | Bundler |
| Axios | 1.7 | Cliente HTTP |
| SCSS | 1.77 | Estilos |
| React Hook Form | 7.52 | Formularios |
| React Toastify | 10.0 | Notificaciones |
| Recharts | 2.12 | Gráficos |

