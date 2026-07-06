package cl.duocuc.sged.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTOs para el dashboard y reportes.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadisticasCursoDTO {
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
public class DesempenoEstudianteDTO {
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
public class AlertaBienestarDTO {
    private Long estudianteId;
    private String nombreEstudiante;
    private String tipo; // "Bajo_Desempeño", "Inasistencia", "Riesgo_Deserción"
    private String descripcion;
    private Integer severidad; // 1 (bajo), 2 (medio), 3 (alto)
}
