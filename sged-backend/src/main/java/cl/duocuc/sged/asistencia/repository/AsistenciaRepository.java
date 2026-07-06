package cl.duocuc.sged.asistencia.repository;

import cl.duocuc.sged.asistencia.entity.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {
    List<Asistencia> findByEstudianteId(Long estudianteId);
    List<Asistencia> findByCursoId(Long cursoId);
    List<Asistencia> findByEstudianteIdAndCursoId(Long estudianteId, Long cursoId);
    List<Asistencia> findByFecha(LocalDate fecha);
    List<Asistencia> findByFechaBetween(LocalDate inicio, LocalDate fin);
}
