package cl.duocuc.sged.usuario.service;

import cl.duocuc.sged.config.JwtConfig;
import cl.duocuc.sged.exception.BadRequestException;
import cl.duocuc.sged.exception.ResourceNotFoundException;
import cl.duocuc.sged.usuario.dto.UsuarioDTO;
import cl.duocuc.sged.usuario.entity.Usuario;
import cl.duocuc.sged.usuario.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtConfig jwtConfig;

    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        usuarioService = new UsuarioService(usuarioRepository, passwordEncoder, jwtConfig);
    }

    @Test
    void obtenerPorId_returnsMappedDtoWhenUserExists() {
        Usuario usuario = usuario(1L, "Ana", "Perez", "ana@example.com", "hash", Usuario.Rol.DOCENTE, true);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        UsuarioDTO resultado = usuarioService.obtenerPorId(1L);

        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getNombre()).isEqualTo("Ana");
        assertThat(resultado.getApellido()).isEqualTo("Perez");
        assertThat(resultado.getEmail()).isEqualTo("ana@example.com");
        assertThat(resultado.getRol()).isEqualTo("DOCENTE");
        assertThat(resultado.getActivo()).isTrue();
    }

    @Test
    void obtenerPorId_throwsWhenUserDoesNotExist() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> usuarioService.obtenerPorId(99L));

        assertThat(exception.getMessage()).isEqualTo("Usuario no encontrado con ID: 99");
    }

    @Test
    void obtenerPorEmail_returnsMappedDtoWhenUserExists() {
        Usuario usuario = usuario(2L, "Luis", "Diaz", "luis@example.com", "hash", Usuario.Rol.ESTUDIANTE, false);
        when(usuarioRepository.findByEmail("luis@example.com")).thenReturn(Optional.of(usuario));

        UsuarioDTO resultado = usuarioService.obtenerPorEmail("luis@example.com");

        assertThat(resultado.getId()).isEqualTo(2L);
        assertThat(resultado.getNombre()).isEqualTo("Luis");
        assertThat(resultado.getRol()).isEqualTo("ESTUDIANTE");
        assertThat(resultado.getActivo()).isFalse();
    }

    @Test
    void obtenerPorRol_usesUppercaseRoleValue() {
        Usuario usuario = usuario(3L, "Maria", "Lopez", "maria@example.com", "hash", Usuario.Rol.DOCENTE, true);
        when(usuarioRepository.findByRol(Usuario.Rol.DOCENTE)).thenReturn(List.of(usuario));

        List<UsuarioDTO> resultado = usuarioService.obtenerPorRol("docente");

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getEmail()).isEqualTo("maria@example.com");
        verify(usuarioRepository).findByRol(Usuario.Rol.DOCENTE);
    }

    @Test
    void crear_throwsWhenEmailAlreadyExists() {
        UsuarioDTO request = usuarioDto(null, "Sara", "Rojas", "sara@example.com", "DOCENTE", null);
        when(usuarioRepository.findByEmail("sara@example.com")).thenReturn(Optional.of(usuario(4L, "Sara", "Rojas", "sara@example.com", "hash", Usuario.Rol.DOCENTE, true)));

        BadRequestException exception = assertThrows(BadRequestException.class, () -> usuarioService.crear(request));

        assertThat(exception.getMessage()).isEqualTo("Email ya registrado: sara@example.com");
        verify(usuarioRepository, never()).save(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void crear_encodesTemporaryPasswordAndSavesUser() {
        UsuarioDTO request = usuarioDto(null, "Carlos", "Mora", "carlos@example.com", "estudiante", null);
        when(usuarioRepository.findByEmail("carlos@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("temporal123")).thenReturn("encoded-password");
        when(usuarioRepository.save(org.mockito.ArgumentMatchers.any(Usuario.class)))
                .thenAnswer(invocation -> {
                    Usuario usuario = invocation.getArgument(0);
                    usuario.setId(10L);
                    return usuario;
                });

        UsuarioDTO resultado = usuarioService.crear(request);

        assertThat(resultado.getId()).isEqualTo(10L);
        assertThat(resultado.getEmail()).isEqualTo("carlos@example.com");
        assertThat(resultado.getRol()).isEqualTo("ESTUDIANTE");
        verify(passwordEncoder).encode("temporal123");
        verify(usuarioRepository).save(org.mockito.ArgumentMatchers.argThat(usuario ->
                usuario.getPassword().equals("encoded-password")
                        && usuario.getRol() == Usuario.Rol.ESTUDIANTE
                        && Boolean.TRUE.equals(usuario.getActivo())));
    }

    @Test
    void actualizar_updatesMutableFields() {
        Usuario existing = usuario(11L, "Jose", "Old", "jose@example.com", "hash", Usuario.Rol.ADMINISTRADOR, true);
        when(usuarioRepository.findById(11L)).thenReturn(Optional.of(existing));
        when(usuarioRepository.save(org.mockito.ArgumentMatchers.any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UsuarioDTO request = usuarioDto(null, "Jose", "New", "ignored@example.com", "ADMINISTRADOR", false);
        UsuarioDTO resultado = usuarioService.actualizar(11L, request);

        assertThat(resultado.getId()).isEqualTo(11L);
        assertThat(resultado.getNombre()).isEqualTo("Jose");
        assertThat(resultado.getApellido()).isEqualTo("New");
        assertThat(resultado.getActivo()).isFalse();
        assertThat(existing.getEmail()).isEqualTo("jose@example.com");
        verify(usuarioRepository).save(existing);
    }

    @Test
    void cambiarContrasena_updatesPasswordWhenOldPasswordMatches() {
        Usuario existing = usuario(12L, "Pedro", "Castro", "pedro@example.com", "old-hash", Usuario.Rol.DOCENTE, true);
        when(usuarioRepository.findById(12L)).thenReturn(Optional.of(existing));
        when(passwordEncoder.matches("old-pass", "old-hash")).thenReturn(true);
        when(passwordEncoder.encode("new-pass")).thenReturn("new-hash");

        usuarioService.cambiarContrasena(12L, "old-pass", "new-pass");

        assertThat(existing.getPassword()).isEqualTo("new-hash");
        verify(passwordEncoder).matches("old-pass", "old-hash");
        verify(passwordEncoder).encode("new-pass");
        verify(usuarioRepository).save(existing);
    }

    @Test
    void cambiarContrasena_throwsWhenOldPasswordDoesNotMatch() {
        Usuario existing = usuario(13L, "Pedro", "Castro", "pedro@example.com", "old-hash", Usuario.Rol.DOCENTE, true);
        when(usuarioRepository.findById(13L)).thenReturn(Optional.of(existing));
        when(passwordEncoder.matches("wrong-pass", "old-hash")).thenReturn(false);

        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> usuarioService.cambiarContrasena(13L, "wrong-pass", "new-pass"));

        assertThat(exception.getMessage()).isEqualTo("Contraseña actual incorrecta");
        verify(passwordEncoder, never()).encode("new-pass");
        verify(usuarioRepository, never()).save(existing);
    }

    @Test
    void desactivar_marksUserInactive() {
        Usuario existing = usuario(14L, "Luisa", "Toro", "luisa@example.com", "hash", Usuario.Rol.ESTUDIANTE, true);
        when(usuarioRepository.findById(14L)).thenReturn(Optional.of(existing));

        usuarioService.desactivar(14L);

        assertThat(existing.getActivo()).isFalse();
        verify(usuarioRepository).save(existing);
    }

    private Usuario usuario(Long id, String nombre, String apellido, String email, String password, Usuario.Rol rol, Boolean activo) {
        return Usuario.builder()
                .id(id)
                .nombre(nombre)
                .apellido(apellido)
                .email(email)
                .password(password)
                .rol(rol)
                .activo(activo)
                .build();
    }

    private UsuarioDTO usuarioDto(Long id, String nombre, String apellido, String email, String rol, Boolean activo) {
        return UsuarioDTO.builder()
                .id(id)
                .nombre(nombre)
                .apellido(apellido)
                .email(email)
                .rol(rol)
                .activo(activo)
                .build();
    }
}
