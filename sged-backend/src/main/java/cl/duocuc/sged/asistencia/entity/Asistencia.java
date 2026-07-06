package cl.duocuc.sged.asistencia.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

/**
 * Entidad Asistencia - Registra la asistencia de estudiantes.
 */
@Entity
@Table(name = "asistencias")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Long estudianteId;

    @NotNull
    @Column(nullable = false)
    private Long cursoId;

    @NotNull
    @Column(nullable = false)
    private LocalDate fecha;

    @NotNull
    @Column(nullable = false)
    private Boolean presente; // true = presente, false = ausente

    @Column(length = 200)
    private String razon; // Motivo de la ausencia
}
