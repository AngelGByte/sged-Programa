package cl.duocuc.sged.calificacion.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

/**
 * Entidad Calificación - Representa una nota/calificación de un estudiante.
 */
@Entity
@Table(name = "calificaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Long estudianteId;

    @NotNull
    @Column(nullable = false)
    private Long docenteId;

    @NotNull
    @Column(nullable = false)
    private Long cursoId;

    @NotBlank
    @Column(nullable = false)
    private String asignatura;

    @NotBlank
    @Column(nullable = false)
    private String tipoEvaluacion; // "Prueba", "Tarea", "Control", "Examen"

    @NotNull
    @DecimalMin("1.0")
    @DecimalMax("7.0")
    @Column(nullable = false)
    private Double nota;

    @NotNull
    @Column(nullable = false)
    private LocalDate fecha;

    @Column(length = 500)
    private String observaciones;
}
