package cl.duocuc.sged.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTOs para el dashboard y reportes.
 */
public class DashboardDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class EstadisticasCurso {
        private Long cursoId;
        private String nombreCurso;
        private Integer totalEstudiantes;
        private Double promedioCurso;
        private Double porcentajeAsistencia;
        private Integer estudiantesAprueban;
        private Integer estudiantesRepiten;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DesempenoEstudiante {
        private Long estudianteId;
        private String nombreEstudiante;
        private Long cursoId;
        private String nombreCurso;
        private Double promedio;
        private Double porcentajeAsistencia;
        private String estado; // "Aprobado", "Reprobado", "En Riesgo"
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AlertaBienestar {
        private Long estudianteId;
        private String nombreEstudiante;
        private String tipo; // "Bajo_Desempeño", "Inasistencia", "Riesgo_Deserción"
        private String descripcion;
        private Integer severidad; // 1 (bajo), 2 (medio), 3 (alto)
    }
}
