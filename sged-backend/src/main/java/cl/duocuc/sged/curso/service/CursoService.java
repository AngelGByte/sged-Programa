package cl.duocuc.sged.curso.service;

import cl.duocuc.sged.curso.entity.Curso;
import cl.duocuc.sged.curso.repository.CursoRepository;
import cl.duocuc.sged.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CursoService {

    private final CursoRepository cursoRepository;

    public Curso obtenerPorId(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado: " + id));
    }

    public List<Curso> obtenerTodos() {
        return cursoRepository.findAll();
    }

    public List<Curso> obtenerActivos() {
        return cursoRepository.findByActivo(true);
    }

    public List<Curso> obtenerPorDocente(Long docenteId) {
        return cursoRepository.findByDocenteJefeId(docenteId);
    }

    public Curso crear(Curso curso) {
        return cursoRepository.save(curso);
    }

    public Curso actualizar(Long id, Curso cursoActualizado) {
        Curso curso = obtenerPorId(id);
        curso.setNombre(cursoActualizado.getNombre());
        curso.setNivel(cursoActualizado.getNivel());
        curso.setLetra(cursoActualizado.getLetra());
        curso.setDocenteJefeId(cursoActualizado.getDocenteJefeId());
        curso.setAnio(cursoActualizado.getAnio());
        curso.setActivo(cursoActualizado.getActivo());
        return cursoRepository.save(curso);
    }

    public void desactivar(Long id) {
        Curso curso = obtenerPorId(id);
        curso.setActivo(false);
        cursoRepository.save(curso);
    }
}
