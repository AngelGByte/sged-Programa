package cl.duocuc.sged.asistencia.controller;

import cl.duocuc.sged.asistencia.entity.Asistencia;
import cl.duocuc.sged.asistencia.service.AsistenciaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asistencias")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
public class AsistenciaController {

    private final AsistenciaService asistenciaService;

    @GetMapping
    public ResponseEntity<List<Asistencia>> obtenerTodas() {
        return ResponseEntity.ok(asistenciaService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Asistencia> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(asistenciaService.obtenerPorId(id));
    }

    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<List<Asistencia>> obtenerPorEstudiante(@PathVariable Long estudianteId) {
        return ResponseEntity.ok(asistenciaService.obtenerPorEstudiante(estudianteId));
    }

    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<List<Asistencia>> obtenerPorCurso(@PathVariable Long cursoId) {
        return ResponseEntity.ok(asistenciaService.obtenerPorCurso(cursoId));
    }

    @GetMapping("/estudiante/{estudianteId}/curso/{cursoId}")
    public ResponseEntity<List<Asistencia>> obtenerPorEstudianteYCurso(
            @PathVariable Long estudianteId,
            @PathVariable Long cursoId) {
        return ResponseEntity.ok(asistenciaService.obtenerPorEstudianteYCurso(estudianteId, cursoId));
    }

    @GetMapping("/estudiante/{estudianteId}/curso/{cursoId}/porcentaje")
    public ResponseEntity<Double> calcularPorcentajeAsistencia(
            @PathVariable Long estudianteId,
            @PathVariable Long cursoId) {
        Double porcentaje = asistenciaService.calcularPorcentajeAsistencia(estudianteId, cursoId);
        return ResponseEntity.ok(porcentaje);
    }

    @PostMapping
    @PreAuthorize("hasRole('DOCENTE') or hasRole('ADMINISTRADOR')")
    public ResponseEntity<Asistencia> crear(@RequestBody Asistencia asistencia) {
        Asistencia asistenciaCreada = asistenciaService.crear(asistencia);
        return ResponseEntity.status(HttpStatus.CREATED).body(asistenciaCreada);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('DOCENTE') or hasRole('ADMINISTRADOR')")
    public ResponseEntity<Asistencia> actualizar(
            @PathVariable Long id,
            @RequestBody Asistencia asistencia) {
        Asistencia asistenciaActualizada = asistenciaService.actualizar(id, asistencia);
        return ResponseEntity.ok(asistenciaActualizada);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        asistenciaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
