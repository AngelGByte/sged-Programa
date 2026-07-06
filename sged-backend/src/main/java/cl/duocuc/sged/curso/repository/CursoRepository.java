package cl.duocuc.sged.curso.repository;

import cl.duocuc.sged.curso.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    List<Curso> findByActivo(Boolean activo);
    List<Curso> findByDocenteJefeId(Long docenteJefeId);
}
