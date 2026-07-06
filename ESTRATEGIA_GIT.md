# Estrategia de Branching y Control de Versiones — SGED

## Modelo de Branching: Git Flow

Usamos **Git Flow** para organizarnos en equipo de manera ordenada y profesional.

---

## Ramas Principales

### 1. `main` (Producción)

- ✅ **Estable y lista para deploy**
- Solo código probado y revisado
- No se hace commit directo aquí
- Se actualiza desde `release/*` o `hotfix/*`

```bash
# Ver historial de main
git log --oneline main
```

### 2. `develop` (Desarrollo)

- 🔧 **Rama de integración**
- Contiene las últimas características
- Base para crear feature branches
- Se actualiza desde `feature/*` y `release/*`

```bash
# Mantenerla siempre actualizada
git checkout develop
git pull origin develop
```

---

## Ramas de Trabajo

### Feature Branch (Características Nuevas)

**Patrón**: `feature/<nombre-funcionalidad>`

```bash
# Crear desde develop
git checkout develop
git pull origin develop
git checkout -b feature/agregar-dashboard

# Trabajar
git add .
git commit -m "feat: agregar dashboard de estadísticas"

# Publicar
git push origin feature/agregar-dashboard

# Al terminar, crear Pull Request (PR) a develop
```

**Ejemplos de nombres**:
- `feature/modulo-bienestar`
- `feature/calificaciones-en-tiempo-real`
- `feature/reporte-asistencias`
- `feature/autenticacion-2fa`

### Bug Fix Branch (Correcciones)

**Patrón**: `bugfix/<nombre-bug>`

```bash
git checkout develop
git checkout -b bugfix/error-calculo-promedio

git add .
git commit -m "fix: corregir cálculo de promedio en calificaciones"

git push origin bugfix/error-calculo-promedio
```

### Release Branch (Preparar Lanzamiento)

**Patrón**: `release/<version>`

```bash
# Crear desde develop
git checkout develop
git checkout -b release/1.0.0

# Cambios finales (version bumps, release notes)
git add .
git commit -m "chore: release v1.0.0"

git push origin release/1.0.0
```

Luego:
1. Merge a `main` con tag
2. Merge de vuelta a `develop`

### Hotfix Branch (Parches en Producción)

**Patrón**: `hotfix/<nombre-urgente>`

```bash
# Crear desde main (¡no desde develop!)
git checkout main
git checkout -b hotfix/error-critico-login

git add .
git commit -m "hotfix: resolver error de autenticación"

git push origin hotfix/error-critico-login
```

Luego:
1. Merge a `main` con tag
2. Merge a `develop`

---

## Flujo de Trabajo Diario

### 1. Empezar a trabajar en una feature

```bash
# Actualizar develop
git checkout develop
git pull origin develop

# Crear feature branch
git checkout -b feature/nueva-funcionalidad

# Hacer cambios
# ...

# Guardar localmente
git add .
git commit -m "feat: descripción del cambio"

# Publicar a GitHub
git push origin feature/nueva-funcionalidad
```

### 2. Crear Pull Request

En GitHub:
1. Ir a **Pull Requests**
2. Clic en **New Pull Request**
3. Comparar `feature/...` con `develop`
4. Añadir título descriptivo
5. Describir cambios
6. Esperar revisión

### 3. Código Review

**Revisor verifica**:
- ✅ El código es correcto
- ✅ Sigue convenciones
- ✅ No rompe nada existente
- ✅ Está documentado

Si hay cambios solicitados:

```bash
# Hacer cambios en local
git add .
git commit -m "review: ajustar según comentarios"

git push origin feature/nueva-funcionalidad
```

### 4. Merge

Una vez aprobado:

```bash
# Si quieres hacer merge local
git checkout develop
git pull origin develop
git merge --no-ff feature/nueva-funcionalidad

git push origin develop

# Luego eliminar la rama
git push origin -d feature/nueva-funcionalidad
git branch -d feature/nueva-funcionalidad
```

O hacer merge directo desde GitHub (botón "Merge Pull Request").

---

## Convenciones de Commits

**Formato**: `<tipo>: <descripción>`

### Tipos

- `feat:` — Nueva funcionalidad
- `fix:` — Corrección de bug
- `docs:` — Documentación
- `style:` — Cambios de formato (no lógica)
- `refactor:` — Reestructuración de código
- `perf:` — Optimización de rendimiento
- `test:` — Agregar/modificar tests
- `chore:` — Tareas de mantenimiento

### Ejemplos

```bash
# ✅ Bueno
git commit -m "feat: agregar filtro de calificaciones por curso"
git commit -m "fix: corregir cálculo de promedio decimal"
git commit -m "docs: actualizar arquitectura general"
git commit -m "refactor: simplificar lógica del service"

# ❌ Malo
git commit -m "cambios varias"
git commit -m "arreglar stuff"
git commit -m "update code"
```

---

## Versionado Semántico

**Formato**: `MAJOR.MINOR.PATCH`

- `MAJOR` — Cambios incompatibles (1.0.0 → 2.0.0)
- `MINOR` — Nuevas funcionalidades (1.0.0 → 1.1.0)
- `PATCH` — Bug fixes (1.0.0 → 1.0.1)

```bash
# Tags en main
git tag v1.0.0
git push origin v1.0.0

# Ver tags
git tag -l
```

---

## Flujo de Release

### Preparar v1.1.0

```bash
# 1. Crear release branch
git checkout develop
git checkout -b release/1.1.0

# 2. Actualizar versión en pom.xml y package.json
# sged-backend/pom.xml: <version>1.1.0</version>
# sged-frontend/package.json: "version": "1.1.0"

# 3. Commit
git add .
git commit -m "chore: bump version to 1.1.0"
git push origin release/1.1.0
```

### Mergear a main

```bash
# 4. Merge a main
git checkout main
git pull origin main
git merge --no-ff release/1.1.0

# 5. Tag
git tag -a v1.1.0 -m "Release v1.1.0"
git push origin main
git push origin v1.1.0

# 6. Merge de vuelta a develop
git checkout develop
git merge --no-ff release/1.1.0
git push origin develop

# 7. Eliminar rama
git push origin -d release/1.1.0
git branch -d release/1.1.0
```

---

## Configuración de GitHub (Protecciones)

**Para proteger branches**:

1. Ir a **Settings → Branches**
2. Seleccionar `main` y `develop`
3. Habilitar:
   - ✅ **Require pull request reviews before merging**
   - ✅ **Require status checks to pass before merging**
   - ✅ **Require branches to be up to date**
   - ✅ **Include administrators**

---

## Checklist para Devs

Antes de hacer commit:

- [ ] ¿El código funciona?
- [ ] ¿Sigue las convenciones de nombres?
- [ ] ¿Está documentado?
- [ ] ¿Hay console.logs o código de prueba?
- [ ] ¿Se probó en otro navegador?
- [ ] ¿El commit message es claro?

Antes de hacer merge a develop:

- [ ] ¿PR tiene descripción clara?
- [ ] ¿Fue revisado por alguien?
- [ ] ¿Pasan todos los tests?
- [ ] ¿No hay conflictos?

---

## Comando Útiles

```bash
# Ver ramas locales
git branch -a

# Ver historial
git log --oneline --graph --all

# Ver cambios sin staged
git diff

# Ver cambios staged
git diff --cached

# Descartar cambios locales
git checkout .

# Traer cambios del remoto sin mergear
git fetch origin

# Rebase (reordenar commits)
git rebase origin/develop

# Squash (comprimir commits)
git rebase -i HEAD~3

# Ver quién hizo cambios en una línea
git blame archivo.java

# Búsqueda de cambios
git log -p -S "texto-a-buscar"
```

---

## Problemas Comunes

### Merge conflicts

```bash
# Abrir archivo y resolver conflictos manualmente
# Luego:
git add .
git commit -m "merge: resolver conflictos"
git push
```

### Se olvidó cambio en commit anterior

```bash
# Opción 1: Nuevo commit (recomendado)
git add .
git commit -m "feat: completar funcionalidad"

# Opción 2: Amend (peligroso si ya está pusheado)
git add .
git commit --amend --no-edit
```

### Deshacer último push

```bash
# ⚠️ PELIGRO: solo si no fue pusheado a main/develop
git reset HEAD~1
```

