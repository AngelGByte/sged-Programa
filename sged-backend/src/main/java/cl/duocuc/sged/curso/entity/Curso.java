package cl.duocuc.sged.curso.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Entidad Curso - Representa un curso en el sistema.
 */
@Entity
@Table(name = "cursos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String nombre; // Ej: "1°A", "2°B"

    @NotBlank
    @Column(nullable = false)
    private String nivel; // Ej: "1°", "2°", "3°"

    @NotBlank
    @Column(nullable = false)
    private String letra; // Ej: "A", "B", "C"

    @NotNull
    @Column(nullable = false)
    private Long docenteJefeId;

    @NotNull
    @Column(nullable = false)
    private Integer anio;

    @Column(nullable = false)
    @Builder.Default
    private Boolean activo = true;
}
