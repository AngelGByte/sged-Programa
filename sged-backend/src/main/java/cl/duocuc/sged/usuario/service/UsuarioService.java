package cl.duocuc.sged.usuario.service;

import cl.duocuc.sged.config.JwtConfig;
import cl.duocuc.sged.exception.BadRequestException;
import cl.duocuc.sged.exception.ResourceNotFoundException;
import cl.duocuc.sged.usuario.dto.UsuarioDTO;
import cl.duocuc.sged.usuario.entity.Usuario;
import cl.duocuc.sged.usuario.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio de Usuario.
 * Aquí vive toda la lógica de negocio relacionada con usuarios.
 * 
 * Patrón: Chef (Service) → Valida reglas, aplica lógica, delega a Bodega (Repository).
 */
@Service
@AllArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtConfig jwtConfig;

    /**
     * Obtiene un usuario por ID.
     */
    public UsuarioDTO obtenerPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
        return convertirADTO(usuario);
    }

    /**
     * Obtiene un usuario por email.
     */
    public UsuarioDTO obtenerPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con email: " + email));
        return convertirADTO(usuario);
    }

    /**
     * Obtiene todos los usuarios.
     */
    public List<UsuarioDTO> obtenerTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene usuarios por rol.
     */
    public List<UsuarioDTO> obtenerPorRol(String rol) {
        Usuario.Rol rolEnum = Usuario.Rol.valueOf(rol.toUpperCase());
        return usuarioRepository.findByRol(rolEnum)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Crea un nuevo usuario (registro).
     */
    public UsuarioDTO crear(UsuarioDTO usuarioDTO) {
        // Validar que no exista un usuario con ese email
        if (usuarioRepository.findByEmail(usuarioDTO.getEmail()).isPresent()) {
            throw new BadRequestException("Email ya registrado: " + usuarioDTO.getEmail());
        }

        Usuario usuario = Usuario.builder()
                .nombre(usuarioDTO.getNombre())
                .apellido(usuarioDTO.getApellido())
                .email(usuarioDTO.getEmail())
                .password(passwordEncoder.encode("temporal123")) // Contraseña temporal
                .rol(Usuario.Rol.valueOf(usuarioDTO.getRol().toUpperCase()))
                .activo(true)
                .build();

        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return convertirADTO(usuarioGuardado);
    }

    /**
     * Actualiza un usuario existente.
     */
    public UsuarioDTO actualizar(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));

        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setActivo(usuarioDTO.getActivo());

        Usuario usuarioActualizado = usuarioRepository.save(usuario);
        return convertirADTO(usuarioActualizado);
    }

    /**
     * Cambia la contraseña de un usuario.
     */
    public void cambiarContrasena(Long id, String passwordAntigua, String passwordNueva) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));

        if (!passwordEncoder.matches(passwordAntigua, usuario.getPassword())) {
            throw new BadRequestException("Contraseña actual incorrecta");
        }

        usuario.setPassword(passwordEncoder.encode(passwordNueva));
        usuarioRepository.save(usuario);
    }

    /**
     * Desactiva un usuario (soft delete).
     */
    public void desactivar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }

    /**
     * Convierte una entidad Usuario a DTO.
     */
    private UsuarioDTO convertirADTO(Usuario usuario) {
        return UsuarioDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .email(usuario.getEmail())
                .rol(usuario.getRol().toString())
                .activo(usuario.getActivo())
                .build();
    }
}
