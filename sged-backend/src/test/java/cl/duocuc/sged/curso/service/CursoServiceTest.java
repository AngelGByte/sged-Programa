package cl.duocuc.sged.curso.service;

import cl.duocuc.sged.curso.entity.Curso;
import cl.duocuc.sged.curso.repository.CursoRepository;
import cl.duocuc.sged.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CursoServiceTest {

    @Mock
    private CursoRepository cursoRepository;

    private CursoService cursoService;

    @BeforeEach
    void setUp() {
        cursoService = new CursoService(cursoRepository);
    }

    @Test
    void obtenerPorId_returnsCourseWhenFound() {
        Curso curso = curso(1L, "1A", "1°", "A", 10L, 2026, true);
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));

        Curso resultado = cursoService.obtenerPorId(1L);

        assertThat(resultado).isSameAs(curso);
        assertThat(resultado.getNombre()).isEqualTo("1A");
    }

    @Test
    void obtenerPorId_throwsWhenCourseMissing() {
        when(cursoRepository.findById(99L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> cursoService.obtenerPorId(99L));

        assertThat(exception.getMessage()).isEqualTo("Curso no encontrado: 99");
    }

    @Test
    void obtenerTodos_returnsRepositoryList() {
        Curso curso = curso(2L, "2B", "2°", "B", 11L, 2026, true);
        when(cursoRepository.findAll()).thenReturn(List.of(curso));

        List<Curso> resultado = cursoService.obtenerTodos();

        assertThat(resultado).containsExactly(curso);
    }

    @Test
    void obtenerActivos_filtersByActiveFlag() {
        Curso curso = curso(3L, "3C", "3°", "C", 12L, 2026, true);
        when(cursoRepository.findByActivo(true)).thenReturn(List.of(curso));

        List<Curso> resultado = cursoService.obtenerActivos();

        assertThat(resultado).containsExactly(curso);
        verify(cursoRepository).findByActivo(true);
    }

    @Test
    void obtenerPorDocente_usesRepositoryLookup() {
        Curso curso = curso(4L, "4D", "4°", "D", 13L, 2026, true);
        when(cursoRepository.findByDocenteJefeId(55L)).thenReturn(List.of(curso));

        List<Curso> resultado = cursoService.obtenerPorDocente(55L);

        assertThat(resultado).containsExactly(curso);
        verify(cursoRepository).findByDocenteJefeId(55L);
    }

    @Test
    void crear_savesCourse() {
        Curso request = curso(null, "5E", "5°", "E", 14L, 2026, true);
        when(cursoRepository.save(any(Curso.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Curso resultado = cursoService.crear(request);

        assertThat(resultado).isSameAs(request);
        verify(cursoRepository).save(request);
    }

    @Test
    void actualizar_copiesMutableFields() {
        Curso existing = curso(5L, "Old", "1°", "A", 20L, 2025, true);
        Curso updated = curso(null, "New", "2°", "B", 30L, 2026, false);
        when(cursoRepository.findById(5L)).thenReturn(Optional.of(existing));
        when(cursoRepository.save(any(Curso.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Curso resultado = cursoService.actualizar(5L, updated);

        assertThat(resultado.getNombre()).isEqualTo("New");
        assertThat(resultado.getNivel()).isEqualTo("2°");
        assertThat(resultado.getLetra()).isEqualTo("B");
        assertThat(resultado.getDocenteJefeId()).isEqualTo(30L);
        assertThat(resultado.getAnio()).isEqualTo(2026);
        assertThat(resultado.getActivo()).isFalse();
    }

    @Test
    void desactivar_setsActiveFlagFalse() {
        Curso existing = curso(6L, "6F", "6°", "F", 40L, 2026, true);
        when(cursoRepository.findById(6L)).thenReturn(Optional.of(existing));

        cursoService.desactivar(6L);

        assertThat(existing.getActivo()).isFalse();
        verify(cursoRepository).save(existing);
    }

    private Curso curso(Long id, String nombre, String nivel, String letra, Long docenteJefeId, Integer anio, Boolean activo) {
        return Curso.builder()
                .id(id)
                .nombre(nombre)
                .nivel(nivel)
                .letra(letra)
                .docenteJefeId(docenteJefeId)
                .anio(anio)
                .activo(activo)
                .build();
    }
}
