package cl.duocuc.sged.calificacion.service;

import cl.duocuc.sged.calificacion.entity.Calificacion;
import cl.duocuc.sged.calificacion.repository.CalificacionRepository;
import cl.duocuc.sged.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class CalificacionService {

    private final CalificacionRepository calificacionRepository;

    public Calificacion obtenerPorId(Long id) {
        return calificacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Calificación no encontrada: " + id));
    }

    public List<Calificacion> obtenerTodas() {
        return calificacionRepository.findAll();
    }

    public List<Calificacion> obtenerPorEstudiante(Long estudianteId) {
        return calificacionRepository.findByEstudianteId(estudianteId);
    }

    public List<Calificacion> obtenerPorDocente(Long docenteId) {
        return calificacionRepository.findByDocenteId(docenteId);
    }

    public List<Calificacion> obtenerPorCurso(Long cursoId) {
        return calificacionRepository.findByCursoId(cursoId);
    }

    public List<Calificacion> obtenerPorEstudianteYCurso(Long estudianteId, Long cursoId) {
        return calificacionRepository.findByEstudianteIdAndCursoId(estudianteId, cursoId);
    }

    public Calificacion crear(Calificacion calificacion) {
        return calificacionRepository.save(calificacion);
    }

    public Calificacion actualizar(Long id, Calificacion calificacionActualizada) {
        Calificacion calificacion = obtenerPorId(id);
        calificacion.setNota(calificacionActualizada.getNota());
        calificacion.setTipoEvaluacion(calificacionActualizada.getTipoEvaluacion());
        calificacion.setObservaciones(calificacionActualizada.getObservaciones());
        return calificacionRepository.save(calificacion);
    }

    public void eliminar(Long id) {
        calificacionRepository.deleteById(id);
    }

    /**
     * Calcula el promedio de calificaciones de un estudiante en un curso.
     */
    public Double calcularPromedio(Long estudianteId, Long cursoId) {
        List<Calificacion> calificaciones = obtenerPorEstudianteYCurso(estudianteId, cursoId);
        if (calificaciones.isEmpty()) {
            return 0.0;
        }
        return calificaciones.stream()
                .mapToDouble(Calificacion::getNota)
                .average()
                .orElse(0.0);
    }
}
