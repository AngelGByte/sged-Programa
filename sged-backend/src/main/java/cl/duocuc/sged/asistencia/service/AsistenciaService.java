package cl.duocuc.sged.asistencia.service;

import cl.duocuc.sged.asistencia.entity.Asistencia;
import cl.duocuc.sged.asistencia.repository.AsistenciaRepository;
import cl.duocuc.sged.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class AsistenciaService {

    private final AsistenciaRepository asistenciaRepository;

    public Asistencia obtenerPorId(Long id) {
        return asistenciaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asistencia no encontrada: " + id));
    }

    public List<Asistencia> obtenerTodas() {
        return asistenciaRepository.findAll();
    }

    public List<Asistencia> obtenerPorEstudiante(Long estudianteId) {
        return asistenciaRepository.findByEstudianteId(estudianteId);
    }

    public List<Asistencia> obtenerPorCurso(Long cursoId) {
        return asistenciaRepository.findByCursoId(cursoId);
    }

    public List<Asistencia> obtenerPorEstudianteYCurso(Long estudianteId, Long cursoId) {
        return asistenciaRepository.findByEstudianteIdAndCursoId(estudianteId, cursoId);
    }

    public Asistencia crear(Asistencia asistencia) {
        return asistenciaRepository.save(asistencia);
    }

    public Asistencia actualizar(Long id, Asistencia asistenciaActualizada) {
        Asistencia asistencia = obtenerPorId(id);
        asistencia.setPresente(asistenciaActualizada.getPresente());
        asistencia.setRazon(asistenciaActualizada.getRazon());
        return asistenciaRepository.save(asistencia);
    }

    public void eliminar(Long id) {
        asistenciaRepository.deleteById(id);
    }

    /**
     * Calcula el porcentaje de asistencia de un estudiante en un curso.
     */
    public Double calcularPorcentajeAsistencia(Long estudianteId, Long cursoId) {
        List<Asistencia> registros = obtenerPorEstudianteYCurso(estudianteId, cursoId);
        if (registros.isEmpty()) {
            return 0.0;
        }
        long presentes = registros.stream().filter(Asistencia::getPresente).count();
        return (presentes * 100.0) / registros.size();
    }
}
