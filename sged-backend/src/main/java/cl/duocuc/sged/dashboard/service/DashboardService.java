package cl.duocuc.sged.dashboard.service;

import cl.duocuc.sged.asistencia.repository.AsistenciaRepository;
import cl.duocuc.sged.asistencia.service.AsistenciaService;
import cl.duocuc.sged.calificacion.repository.CalificacionRepository;
import cl.duocuc.sged.calificacion.service.CalificacionService;
import cl.duocuc.sged.curso.repository.CursoRepository;
import cl.duocuc.sged.dashboard.dto.AlertaBienestarDTO;
import cl.duocuc.sged.dashboard.dto.DesempenoEstudianteDTO;
import cl.duocuc.sged.dashboard.dto.EstadisticasCursoDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio de Dashboard.
 * Proporciona estadísticas y reportes para docentes y administradores.
 */
@Service
@AllArgsConstructor
public class DashboardService {

    private final CursoRepository cursoRepository;
    private final CalificacionService calificacionService;
    private final AsistenciaService asistenciaService;
    private final CalificacionRepository calificacionRepository;
    private final AsistenciaRepository asistenciaRepository;

    /**
     * Obtiene estadísticas de un curso.
     */
    public EstadisticasCursoDTO obtenerEstadisticasCurso(Long cursoId) {
        // Implementación simplificada (en producción, usaría queries más eficientes)
        var curso = cursoRepository.findById(cursoId);

        if (curso.isEmpty()) {
            return null;
        }

        // Aquí iría lógica para calcular:
        // - totalEstudiantes
        // - promedioCurso
        // - porcentajeAsistencia
        // - etc

        return EstadisticasCursoDTO.builder()
                .cursoId(cursoId)
                .nombreCurso(curso.get().getNombre())
                .totalEstudiantes(0)
                .promedioCurso(0.0)
                .porcentajeAsistencia(0.0)
                .estudiantesAprueban(0)
                .estudiantesRepiten(0)
                .build();
    }

    /**
     * Obtiene desempeño de todos los estudiantes en un curso.
     */
    public List<DesempenoEstudianteDTO> obtenerDesempenioCurso(Long cursoId) {
        // Aquí iría la lógica para compilar desempeño de cada estudiante
        return new ArrayList<>();
    }

    /**
     * Obtiene alertas de bienestar de estudiantes en riesgo.
     */
    public List<AlertaBienestarDTO> obtenerAlertasBienestar(Long cursoId) {
        List<AlertaBienestarDTO> alertas = new ArrayList<>();

        // Lógica para identificar estudiantes en riesgo:
        // - Bajo desempeño académico (promedio < 4.0)
        // - Inasistencia frecuente (< 75%)
        // - Riesgo de deserción (combinación de factores)

        return alertas;
    }

    /**
     * Genera reporte de desempeño general por docente.
     */
    public List<EstadisticasCursoDTO> obtenerReportePorDocente(Long docenteId) {
        var cursosPorDocente = cursoRepository.findByDocenteJefeId(docenteId);
        return cursosPorDocente.stream()
                .map(c -> obtenerEstadisticasCurso(c.getId()))
                .collect(Collectors.toList());
    }
}
