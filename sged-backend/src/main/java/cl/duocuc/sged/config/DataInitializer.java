package cl.duocuc.sged.config;

import cl.duocuc.sged.usuario.entity.Usuario;
import cl.duocuc.sged.usuario.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Inicializador de datos.
 * Se ejecuta una sola vez cuando arranca la aplicación.
 * Carga datos iniciales en la base de datos.
 */
@Configuration
@AllArgsConstructor
public class DataInitializer {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initializeData() {
        return args -> {
            // Si no existen usuarios, crear algunos de prueba
            if (usuarioRepository.count() == 0) {
                
                // Admin
                Usuario admin = Usuario.builder()
                        .nombre("Administrador")
                        .apellido("Sistema")
                        .email("admin@sged.cl")
                        .password(passwordEncoder.encode("admin123"))
                        .rol(Usuario.Rol.ADMINISTRADOR)
                        .activo(true)
                        .build();
                usuarioRepository.save(admin);

                // Docente
                Usuario docente = Usuario.builder()
                        .nombre("Carlos")
                        .apellido("Mendoza")
                        .email("carlos.mendoza@sged.cl")
                        .password(passwordEncoder.encode("docente123"))
                        .rol(Usuario.Rol.DOCENTE)
                        .activo(true)
                        .build();
                usuarioRepository.save(docente);

                // Estudiante
                Usuario estudiante = Usuario.builder()
                        .nombre("Juan")
                        .apellido("González")
                        .email("juan.gonzalez@sged.cl")
                        .password(passwordEncoder.encode("estudiante123"))
                        .rol(Usuario.Rol.ESTUDIANTE)
                        .activo(true)
                        .build();
                usuarioRepository.save(estudiante);

                System.out.println("✅ Datos iniciales cargados exitosamente.");
            }
        };
    }
}
