package cl.duocuc.sged.calificacion.controller;

import cl.duocuc.sged.calificacion.entity.Calificacion;
import cl.duocuc.sged.calificacion.service.CalificacionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/calificaciones")
@AllArgsConstructor
@CrossOrigin(originPatterns = {"http://localhost:*", "http://127.0.0.1:*"})
public class CalificacionController {

    private final CalificacionService calificacionService;

    @GetMapping
    public ResponseEntity<List<Calificacion>> obtenerTodas() {
        return ResponseEntity.ok(calificacionService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Calificacion> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(calificacionService.obtenerPorId(id));
    }

    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<List<Calificacion>> obtenerPorEstudiante(@PathVariable Long estudianteId) {
        return ResponseEntity.ok(calificacionService.obtenerPorEstudiante(estudianteId));
    }

    @GetMapping("/docente/{docenteId}")
    @PreAuthorize("hasRole('DOCENTE') or hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<Calificacion>> obtenerPorDocente(@PathVariable Long docenteId) {
        return ResponseEntity.ok(calificacionService.obtenerPorDocente(docenteId));
    }

    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<List<Calificacion>> obtenerPorCurso(@PathVariable Long cursoId) {
        return ResponseEntity.ok(calificacionService.obtenerPorCurso(cursoId));
    }

    @GetMapping("/estudiante/{estudianteId}/curso/{cursoId}")
    public ResponseEntity<List<Calificacion>> obtenerPorEstudianteYCurso(
            @PathVariable Long estudianteId,
            @PathVariable Long cursoId) {
        return ResponseEntity.ok(calificacionService.obtenerPorEstudianteYCurso(estudianteId, cursoId));
    }

    @GetMapping("/estudiante/{estudianteId}/curso/{cursoId}/promedio")
    public ResponseEntity<Double> calcularPromedio(
            @PathVariable Long estudianteId,
            @PathVariable Long cursoId) {
        Double promedio = calificacionService.calcularPromedio(estudianteId, cursoId);
        return ResponseEntity.ok(promedio);
    }

    @PostMapping
    @PreAuthorize("hasRole('DOCENTE') or hasRole('ADMINISTRADOR')")
    public ResponseEntity<Calificacion> crear(@RequestBody Calificacion calificacion) {
        Calificacion calificacionCreada = calificacionService.crear(calificacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(calificacionCreada);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('DOCENTE') or hasRole('ADMINISTRADOR')")
    public ResponseEntity<Calificacion> actualizar(
            @PathVariable Long id,
            @RequestBody Calificacion calificacion) {
        Calificacion calificacionActualizada = calificacionService.actualizar(id, calificacion);
        return ResponseEntity.ok(calificacionActualizada);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        calificacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
