import axiosConfig from './axiosConfig'

/**
 * Servicios de Autenticación.
 */
export const authService = {
  login: (email, password) =>
    axiosConfig.post('/auth/login', { email, password }),

  register: (nombre, apellido, email, password) =>
    axiosConfig.post('/auth/register', { nombre, apellido, email, password })
}

/**
 * Servicios de Usuario.
 */
export const usuarioService = {
  obtenerTodos: () =>
    axiosConfig.get('/usuarios'),

  obtenerPorId: (id) =>
    axiosConfig.get(`/usuarios/${id}`),

  obtenerPorRol: (rol) =>
    axiosConfig.get(`/usuarios/rol/${rol}`),

  crear: (usuarioDTO) =>
    axiosConfig.post('/usuarios', usuarioDTO),

  actualizar: (id, usuarioDTO) =>
    axiosConfig.put(`/usuarios/${id}`, usuarioDTO),

  desactivar: (id) =>
    axiosConfig.delete(`/usuarios/${id}`)
}

/**
 * Servicios de Curso.
 */
export const cursoService = {
  obtenerTodos: () =>
    axiosConfig.get('/cursos'),

  obtenerActivos: () =>
    axiosConfig.get('/cursos/activos'),

  obtenerPorId: (id) =>
    axiosConfig.get(`/cursos/${id}`),

  obtenerPorDocente: (docenteId) =>
    axiosConfig.get(`/cursos/docente/${docenteId}`),

  crear: (cursoDTO) =>
    axiosConfig.post('/cursos', cursoDTO),

  actualizar: (id, cursoDTO) =>
    axiosConfig.put(`/cursos/${id}`, cursoDTO),

  desactivar: (id) =>
    axiosConfig.delete(`/cursos/${id}`)
}

/**
 * Servicios de Calificación.
 */
export const calificacionService = {
  obtenerTodas: () =>
    axiosConfig.get('/calificaciones'),

  obtenerPorId: (id) =>
    axiosConfig.get(`/calificaciones/${id}`),

  obtenerPorEstudiante: (estudianteId) =>
    axiosConfig.get(`/calificaciones/estudiante/${estudianteId}`),

  obtenerPorCurso: (cursoId) =>
    axiosConfig.get(`/calificaciones/curso/${cursoId}`),

  obtenerPromedio: (estudianteId, cursoId) =>
    axiosConfig.get(`/calificaciones/estudiante/${estudianteId}/curso/${cursoId}/promedio`),

  crear: (calificacionDTO) =>
    axiosConfig.post('/calificaciones', calificacionDTO),

  actualizar: (id, calificacionDTO) =>
    axiosConfig.put(`/calificaciones/${id}`, calificacionDTO),

  eliminar: (id) =>
    axiosConfig.delete(`/calificaciones/${id}`)
}

/**
 * Servicios de Asistencia.
 */
export const asistenciaService = {
  obtenerTodas: () =>
    axiosConfig.get('/asistencias'),

  obtenerPorEstudiante: (estudianteId) =>
    axiosConfig.get(`/asistencias/estudiante/${estudianteId}`),

  obtenerPorCurso: (cursoId) =>
    axiosConfig.get(`/asistencias/curso/${cursoId}`),

  obtenerPorcentaje: (estudianteId, cursoId) =>
    axiosConfig.get(`/asistencias/estudiante/${estudianteId}/curso/${cursoId}/porcentaje`),

  crear: (asistenciaDTO) =>
    axiosConfig.post('/asistencias', asistenciaDTO),

  actualizar: (id, asistenciaDTO) =>
    axiosConfig.put(`/asistencias/${id}`, asistenciaDTO),

  eliminar: (id) =>
    axiosConfig.delete(`/asistencias/${id}`)
}

/**
 * Servicios de Dashboard.
 */
export const dashboardService = {
  obtenerEstadisticasCurso: (cursoId) =>
    axiosConfig.get(`/dashboard/estadisticas-curso/${cursoId}`),

  obtenerDesempenioCurso: (cursoId) =>
    axiosConfig.get(`/dashboard/desempeno-curso/${cursoId}`),

  obtenerAlertasBienestar: (cursoId) =>
    axiosConfig.get(`/dashboard/alertas-bienestar/${cursoId}`),

  obtenerReportePorDocente: (docenteId) =>
    axiosConfig.get(`/dashboard/reporte-docente/${docenteId}`)
}
