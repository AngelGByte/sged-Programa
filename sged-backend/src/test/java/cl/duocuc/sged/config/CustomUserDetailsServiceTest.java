package cl.duocuc.sged.config;

import cl.duocuc.sged.usuario.entity.Usuario;
import cl.duocuc.sged.usuario.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    void setUp() {
        customUserDetailsService = new CustomUserDetailsService(usuarioRepository);
    }

    @Test
    void loadUserByUsername_returnsSecurityUserWhenFound() {
        Usuario usuario = Usuario.builder()
                .id(1L)
                .nombre("Ana").apellido("Perez")
                .email("ana@example.com")
                .password("hashed")
                .rol(Usuario.Rol.ADMINISTRADOR)
                .activo(true)
                .build();
        when(usuarioRepository.findByEmail("ana@example.com")).thenReturn(Optional.of(usuario));

        UserDetails result = customUserDetailsService.loadUserByUsername("ana@example.com");

        assertThat(result.getUsername()).isEqualTo("ana@example.com");
        assertThat(result.getPassword()).isEqualTo("hashed");
        assertThat(result.getAuthorities())
            .extracting("authority")
            .containsExactly("ROLE_ADMINISTRADOR");
    }

    @Test
    void loadUserByUsername_throwsWhenEmailMissing() {
        when(usuarioRepository.findByEmail("missing@example.com")).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,
                () -> customUserDetailsService.loadUserByUsername("missing@example.com"));

        assertThat(exception.getMessage()).isEqualTo("Usuario no encontrado con email: missing@example.com");
    }
}
