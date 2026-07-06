package cl.duocuc.sged.dashboard.controller;

import cl.duocuc.sged.dashboard.dto.AlertaBienestarDTO;
import cl.duocuc.sged.dashboard.dto.DesempenoEstudianteDTO;
import cl.duocuc.sged.dashboard.dto.EstadisticasCursoDTO;
import cl.duocuc.sged.dashboard.service.DashboardService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/estadisticas-curso/{cursoId}")
    @PreAuthorize("hasRole('DOCENTE') or hasRole('ADMINISTRADOR')")
    public ResponseEntity<EstadisticasCursoDTO> obtenerEstadisticasCurso(@PathVariable Long cursoId) {
        EstadisticasCursoDTO estadisticas = dashboardService.obtenerEstadisticasCurso(cursoId);
        return ResponseEntity.ok(estadisticas);
    }

    @GetMapping("/desempeno-curso/{cursoId}")
    @PreAuthorize("hasRole('DOCENTE') or hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<DesempenoEstudianteDTO>> obtenerDesempenioCurso(@PathVariable Long cursoId) {
        List<DesempenoEstudianteDTO> desempeno = dashboardService.obtenerDesempenioCurso(cursoId);
        return ResponseEntity.ok(desempeno);
    }

    @GetMapping("/alertas-bienestar/{cursoId}")
    @PreAuthorize("hasRole('DOCENTE') or hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<AlertaBienestarDTO>> obtenerAlertasBienestar(@PathVariable Long cursoId) {
        List<AlertaBienestarDTO> alertas = dashboardService.obtenerAlertasBienestar(cursoId);
        return ResponseEntity.ok(alertas);
    }

    @GetMapping("/reporte-docente/{docenteId}")
    @PreAuthorize("hasRole('DOCENTE') or hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<EstadisticasCursoDTO>> obtenerReportePorDocente(@PathVariable Long docenteId) {
        List<EstadisticasCursoDTO> reporte = dashboardService.obtenerReportePorDocente(docenteId);
        return ResponseEntity.ok(reporte);
    }
}
