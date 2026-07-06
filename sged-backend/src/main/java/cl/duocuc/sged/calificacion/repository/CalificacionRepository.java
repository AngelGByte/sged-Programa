package cl.duocuc.sged.calificacion.repository;

import cl.duocuc.sged.calificacion.entity.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
    List<Calificacion> findByEstudianteId(Long estudianteId);
    List<Calificacion> findByDocenteId(Long docenteId);
    List<Calificacion> findByCursoId(Long cursoId);
    List<Calificacion> findByEstudianteIdAndCursoId(Long estudianteId, Long cursoId);
    List<Calificacion> findByFechaBetween(LocalDate inicio, LocalDate fin);
}
