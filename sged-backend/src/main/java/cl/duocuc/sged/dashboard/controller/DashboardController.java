package cl.duocuc.sged.dashboard.controller;

import cl.duocuc.sged.dashboard.dto.DashboardDTO;
import cl.duocuc.sged.dashboard.dto.DashboardDTO.AlertaBienestar;
import cl.duocuc.sged.dashboard.dto.DashboardDTO.DesempenoEstudiante;
import cl.duocuc.sged.dashboard.dto.DashboardDTO.EstadisticasCurso;
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
    public ResponseEntity<EstadisticasCurso> obtenerEstadisticasCurso(@PathVariable Long cursoId) {
        EstadisticasCurso estadisticas = dashboardService.obtenerEstadisticasCurso(cursoId);
        return ResponseEntity.ok(estadisticas);
    }

    @GetMapping("/desempeno-curso/{cursoId}")
    @PreAuthorize("hasRole('DOCENTE') or hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<DesempenoEstudiante>> obtenerDesempenioCurso(@PathVariable Long cursoId) {
        List<DesempenoEstudiante> desempeno = dashboardService.obtenerDesempenioCurso(cursoId);
        return ResponseEntity.ok(desempeno);
    }

    @GetMapping("/alertas-bienestar/{cursoId}")
    @PreAuthorize("hasRole('DOCENTE') or hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<AlertaBienestar>> obtenerAlertasBienestar(@PathVariable Long cursoId) {
        List<AlertaBienestar> alertas = dashboardService.obtenerAlertasBienestar(cursoId);
        return ResponseEntity.ok(alertas);
    }

    @GetMapping("/reporte-docente/{docenteId}")
    @PreAuthorize("hasRole('DOCENTE') or hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<EstadisticasCurso>> obtenerReportePorDocente(@PathVariable Long docenteId) {
        List<EstadisticasCurso> reporte = dashboardService.obtenerReportePorDocente(docenteId);
        return ResponseEntity.ok(reporte);
    }
}
