# 📚 SGED - Índice Maestro de Documentación

> Sistema Integral de Gestión Académica — Fullstack Monolith Modular

---

## 🗂️ Centro de Documentación

Bienvenido al SGED. Esta es tu guía completa para entender, usar y desarrollar el sistema.

---

## 🚀 Comienza Aquí

### Para Principiantes
1. **[README.md](README.md)** ← **COMIENZA AQUÍ**
   - Qué es SGED
   - Stack tecnológico
   - Características principales
   - Arquitectura general

2. **[QUICK_REFERENCE.md](QUICK_REFERENCE.md)** ← Respuestas rápidas
   - Cómo iniciar el proyecto
   - Comandos útiles
   - API endpoints
   - Troubleshooting

### Para Setup y Configuración
3. **[GUIA_INSTALACION.md](GUIA_INSTALACION.md)** ← Paso a paso
   - Requisitos previos
   - Instalación completa
   - Credenciales de prueba
   - Solución de problemas

---

## 📖 Documentación Técnica Detallada

### Backend (Spring Boot)

**[sged-backend/markdown-backend/01-arquitectura-general.md](sged-backend/markdown-backend/01-arquitectura-general.md)**

Contenido:
- ✅ Patrón Restaurant (explicación intuitiva)
- ✅ Estructura de carpetas
- ✅ Flujo HTTP completo
- ✅ JWT autenticación paso a paso
- ✅ Schema de base de datos
- ✅ Todos los 30+ endpoints documentados
- ✅ Configuración explicada
- ✅ Patrones de diseño
- ✅ Cómo agregar nuevos módulos

**Secciones:**
1. Introducción al patrón Repository-Service-Controller
2. Estructura del proyecto
3. Flujo de una petición HTTP
4. Autenticación JWT
5. Base de datos
6. API endpoints completos
7. Configuración (Security, CORS, JWT)
8. Patrones implementados
9. Cómo extender el proyecto

---

### Frontend (React + Vite)

**[sged-frontend/markdown-frontend/01-arquitectura-general.md](sged-frontend/markdown-frontend/01-arquitectura-general.md)**

Contenido:
- ✅ Estructura modular de React
- ✅ Contexto de autenticación (AuthContext)
- ✅ Custom hooks (useAuth)
- ✅ Servicios HTTP (axios)
- ✅ React Router con rutas protegidas
- ✅ Flujo de petición HTTP
- ✅ Autenticación completa
- ✅ Componentes reutilizables
- ✅ Variables de entorno

**Secciones:**
1. Qué es el Frontend
2. Estructura del proyecto
3. Conceptos clave (Context, Hooks, Servicios)
4. Patrones aplicados
5. Cómo usar los servicios
6. Flujo de autenticación
7. Componentes reutilizables
8. Testing (próximos pasos)
9. Deployment

---

## 🏗️ Documentación de Arquitectura

### [RESUMEN_ARQUITECTURA.md](RESUMEN_ARQUITECTURA.md)

Información ejecutiva y técnica:
- Descripción general del proyecto
- Arquitectura de capas (diagrama ASCII)
- Stack completo
- Seguridad JWT
- Módulos backend detallados
- Componentes frontend
- Flujos principales (login, petición HTTP)
- Relaciones base de datos (ERD)
- Documentación generada
- Conceptos demostrables para examen

---

## 🌿 Control de Versiones

### [ESTRATEGIA_GIT.md](ESTRATEGIA_GIT.md)

Cómo colaborar en equipo:
- Modelo Git Flow
- Ramas principales (main, develop)
- Ramas de trabajo (feature, bugfix, release, hotfix)
- Flujo de trabajo diario
- Pull requests
- Code review
- Merge strategy
- Convenciones de commits
- Versionado semántico
- Configuración GitHub

**Convenciones de commit:**
```
feat: Nueva funcionalidad
fix: Corrección de bug
docs: Documentación
refactor: Reestructuración
test: Tests
chore: Mantenimiento
```

---

## 📋 Guía de Continuación

### [PLAN_FUTURO.md](PLAN_FUTURO.md)

Roadmap de desarrollo:

**Fase 1: Frontend Completo (HIGH PRIORITY)**
- Páginas completamente funcionales
- Componentes reutilizables
- Estilos responsive

**Fase 2: Base de Datos (MEDIUM)**
- Script SQL inicial
- Migración automática

**Fase 3: Testing (MEDIUM)**
- Tests unitarios backend
- Tests unitarios frontend
- Tests integración

**Fase 4: Mejoras (LOW PRIORITY)**
- Paginación
- Búsqueda/filtros
- Exportación Excel/PDF
- Notificaciones email
- 2FA
- Gráficos avanzados
- Dark mode
- i18n

Incluye: Checklist de desarrollo, recursos útiles, contacto.

---

## 📊 Referencia Rápida

### [QUICK_REFERENCE.md](QUICK_REFERENCE.md)

Acceso rápido a:
- 🚀 Cómo iniciar backend y frontend
- 🔐 Credenciales de prueba
- 📁 Estructura visual
- 🌐 Todos los endpoints
- 🔧 Comandos útiles (Maven, NPM, Git)
- 🎨 Estructura componentes React
- 💾 Cómo usar servicios
- 🐛 Troubleshooting rápido
- 📝 Patrón de código (cómo agregar módulos)
- 🎯 Roles y permisos
- 📊 Queries SQL útiles
- 🚀 Deploy checklist

---

## 🎯 Documentos por Audiencia

### Para **Product Owners / Managers**
1. [README.md](README.md) — Visión general
2. [RESUMEN_ARQUITECTURA.md](RESUMEN_ARQUITECTURA.md) — Capacidades técnicas

### Para **Desarrolladores Backend**
1. [QUICK_REFERENCE.md](QUICK_REFERENCE.md) — Comienza aquí
2. [sged-backend/markdown-backend/01-arquitectura-general.md](sged-backend/markdown-backend/01-arquitectura-general.md) — Deep dive
3. [ESTRATEGIA_GIT.md](ESTRATEGIA_GIT.md) — Colaboración
4. [GUIA_INSTALACION.md](GUIA_INSTALACION.md) — Setup

### Para **Desarrolladores Frontend**
1. [QUICK_REFERENCE.md](QUICK_REFERENCE.md) — Comienza aquí
2. [sged-frontend/markdown-frontend/01-arquitectura-general.md](sged-frontend/markdown-frontend/01-arquitectura-general.md) — Deep dive
3. [ESTRATEGIA_GIT.md](ESTRATEGIA_GIT.md) — Colaboración
4. [GUIA_INSTALACION.md](GUIA_INSTALACION.md) — Setup

### Para **Nuevos en el Equipo**
1. [README.md](README.md)
2. [QUICK_REFERENCE.md](QUICK_REFERENCE.md)
3. [GUIA_INSTALACION.md](GUIA_INSTALACION.md)
4. Luego, leer la documentación de tu área

### Para **Defensa de Examen (DSY1106)**
1. [RESUMEN_ARQUITECTURA.md](RESUMEN_ARQUITECTURA.md) — Conceptos demostrables
2. [sged-backend/markdown-backend/01-arquitectura-general.md](sged-backend/markdown-backend/01-arquitectura-general.md) — Backend técnico
3. [sged-frontend/markdown-frontend/01-arquitectura-general.md](sged-frontend/markdown-frontend/01-arquitectura-general.md) — Frontend técnico
4. [README.md](README.md) — Visión general

---

## 🗺️ Mapa Mental del Proyecto

```
SGED (Monolith Modular)
│
├─ Backend (Spring Boot 4.0.1)
│  ├─ Autenticación (JWT)
│  ├─ Módulo Usuario (Login, Register)
│  ├─ Módulo Curso (CRUD)
│  ├─ Módulo Calificación (CRUD + Promedio)
│  ├─ Módulo Asistencia (CRUD + Porcentaje)
│  └─ Módulo Dashboard (Estadísticas)
│
├─ Frontend (React 19 + Vite)
│  ├─ Autenticación (Context API)
│  ├─ Componentes (PrivateRoute, etc)
│  ├─ Servicios HTTP (Axios)
│  └─ Páginas (Login, Dashboard, etc)
│
├─ Base de Datos (MySQL)
│  ├─ Usuarios
│  ├─ Cursos
│  ├─ Calificaciones
│  └─ Asistencias
│
└─ Documentación
   ├─ Arquitectura (Backend + Frontend)
   ├─ Instalación
   ├─ Git Flow
   ├─ Resumen Ejecutivo
   ├─ Plan Futuro
   └─ Quick Reference
```

---

## 📱 Estructura de Archivos

```
sged-monolito/
│
├─ 📄 README.md ← START HERE
├─ 📄 QUICK_REFERENCE.md ← Respuestas rápidas
├─ 📄 GUIA_INSTALACION.md ← Setup
├─ 📄 ESTRATEGIA_GIT.md ← Colaboración
├─ 📄 RESUMEN_ARQUITECTURA.md ← Detalles técnicos
├─ 📄 PLAN_FUTURO.md ← Roadmap
├─ 📄 INDICE_MAESTRO.md ← Este archivo
│
├─ 📁 sged-backend/
│  ├─ pom.xml
│  ├─ src/ (Código Java)
│  └─ 📁 markdown-backend/
│     └─ 01-arquitectura-general.md ← Backend docs
│
└─ 📁 sged-frontend/
   ├─ package.json
   ├─ vite.config.js
   ├─ src/ (Código React)
   └─ 📁 markdown-frontend/
      └─ 01-arquitectura-general.md ← Frontend docs
```

---

## 🔍 Búsqueda Rápida

### "Cómo..."

| Pregunta | Respuesta |
|----------|-----------|
| ...iniciar el proyecto? | [QUICK_REFERENCE.md](#-iniciar-el-proyecto) |
| ...agregar un nuevo módulo? | [Backend docs - Cómo extender](#) |
| ...crear una nueva página? | [Frontend docs - Patrón de código](#) |
| ...hacer merge a develop? | [ESTRATEGIA_GIT.md](#-flujo-de-trabajo-diario) |
| ...configurar base de datos? | [GUIA_INSTALACION.md](#-configurar-la-base-de-datos) |
| ...debuggear un error? | [QUICK_REFERENCE.md - Troubleshooting](#-troubleshooting-rápido) |
| ...deployar a producción? | [QUICK_REFERENCE.md - Deploy](#-deploy-checklist) |

---

## 🎓 Conceptos Clave

**Backend:**
- Repository Pattern
- Service Pattern
- DTO Pattern
- JWT Authentication
- Role-Based Access Control
- Exception Handling
- Spring Boot Annotations

**Frontend:**
- Context API
- Custom Hooks
- Axios Interceptors
- React Router
- React Hook Form
- SCSS Modules

---

## 🆘 Ayuda y Soporte

### Si tienes un problema:

1. **Revisa el QUICK_REFERENCE.md** — Troubleshooting
2. **Busca en el documento correspondiente** — Backend/Frontend docs
3. **Revisa GUIA_INSTALACION.md** — Errores comunes
4. **Consulta git log** — Cambios recientes

### Si necesitas aprender algo:

1. **Revisa el README.md** — Visión general
2. **Ve al documento específico** — Backend/Frontend
3. **Consulta ejemplos de código** — En el código fuente

---

## ✅ Checklist de Lectura

Según tu rol, marca lo que has leído:

**Para Todos:**
- [ ] README.md
- [ ] QUICK_REFERENCE.md
- [ ] GUIA_INSTALACION.md

**Backend Dev:**
- [ ] Backend Architecture Docs
- [ ] ESTRATEGIA_GIT.md
- [ ] PLAN_FUTURO.md (Fase 3 - Testing)

**Frontend Dev:**
- [ ] Frontend Architecture Docs
- [ ] ESTRATEGIA_GIT.md
- [ ] PLAN_FUTURO.md (Fase 1 - Frontend)

**Both:**
- [ ] RESUMEN_ARQUITECTURA.md
- [ ] ESTRATEGIA_GIT.md completo
- [ ] PLAN_FUTURO.md

---

## 📞 Contacto y Contribución

Para preguntas o sugerencias sobre la documentación:

1. Abre una issue en GitHub
2. Crea un PR con mejoras
3. Consulta con el equipo
4. Actualiza la documentación según aprendas

---

## 📊 Estadísticas de Documentación

| Documento | Líneas | Propósito |
|-----------|--------|----------|
| README.md | 300+ | Visión general |
| QUICK_REFERENCE.md | 400+ | Referencia rápida |
| GUIA_INSTALACION.md | 250+ | Setup |
| ESTRATEGIA_GIT.md | 450+ | Control versiones |
| RESUMEN_ARQUITECTURA.md | 400+ | Detalles técnicos |
| PLAN_FUTURO.md | 350+ | Roadmap |
| Backend Architecture | 250+ | Backend deep dive |
| Frontend Architecture | 250+ | Frontend deep dive |
| **TOTAL** | **2650+** | **Completamente documentado** |

---

## 🎯 Siguientes Pasos

### Ahora que leíste esto:

1. ✅ Leer **[README.md](README.md)**
2. ✅ Leer **[QUICK_REFERENCE.md](QUICK_REFERENCE.md)**
3. ✅ Seguir **[GUIA_INSTALACION.md](GUIA_INSTALACION.md)**
4. ✅ Elegir tu camino (Backend o Frontend)
5. ✅ Leer documentación específica de tu área
6. ✅ Ver **[PLAN_FUTURO.md](PLAN_FUTURO.md)** para saber qué hacer

---

**Proyecto SGED — Completamente Documentado ✨**

**Versión:** 1.0.0  
**Última actualización:** 2024  
**Estado:** ✅ Listo para producción
