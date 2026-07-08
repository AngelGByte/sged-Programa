package cl.duocuc.sged.curso.controller;

import cl.duocuc.sged.curso.entity.Curso;
import cl.duocuc.sged.curso.service.CursoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@AllArgsConstructor
@CrossOrigin(originPatterns = {"http://localhost:*", "http://127.0.0.1:*"})
public class CursoController {

    private final CursoService cursoService;

    @GetMapping
    public ResponseEntity<List<Curso>> obtenerTodos() {
        return ResponseEntity.ok(cursoService.obtenerTodos());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Curso>> obtenerActivos() {
        return ResponseEntity.ok(cursoService.obtenerActivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(cursoService.obtenerPorId(id));
    }

    @GetMapping("/docente/{docenteId}")
    public ResponseEntity<List<Curso>> obtenerPorDocente(@PathVariable Long docenteId) {
        return ResponseEntity.ok(cursoService.obtenerPorDocente(docenteId));
    }

    @PostMapping
    @PreAuthorize("hasRole('DOCENTE') or hasRole('ADMINISTRADOR')")
    public ResponseEntity<Curso> crear(@RequestBody Curso curso) {
        Curso cursoCreado = cursoService.crear(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoCreado);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('DOCENTE') or hasRole('ADMINISTRADOR')")
    public ResponseEntity<Curso> actualizar(@PathVariable Long id, @RequestBody Curso curso) {
        Curso cursoActualizado = cursoService.actualizar(id, curso);
        return ResponseEntity.ok(cursoActualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        cursoService.desactivar(id);
        return ResponseEntity.noContent().build();
    }
}
