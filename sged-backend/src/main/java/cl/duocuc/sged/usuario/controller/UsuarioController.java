package cl.duocuc.sged.usuario.controller;

import cl.duocuc.sged.config.JwtConfig;
import cl.duocuc.sged.exception.BadRequestException;
import cl.duocuc.sged.usuario.dto.UsuarioDTO;
import cl.duocuc.sged.usuario.entity.Usuario;
import cl.duocuc.sged.usuario.repository.UsuarioRepository;
import cl.duocuc.sged.usuario.service.UsuarioService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Controller de Usuario.
 * Patrón: Mesero - Recibe peticiones, delega al Chef (Service), devuelve respuestas.
 */
@RestController
@RequestMapping("/api/usuarios")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtConfig jwtConfig;

    /**
     * GET /api/usuarios/{id} - Obtiene un usuario por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerPorId(@PathVariable Long id) {
        UsuarioDTO usuario = usuarioService.obtenerPorId(id);
        return ResponseEntity.ok(usuario);
    }

    /**
     * GET /api/usuarios - Obtiene todos los usuarios
     */
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> obtenerTodos() {
        List<UsuarioDTO> usuarios = usuarioService.obtenerTodos();
        return ResponseEntity.ok(usuarios);
    }

    /**
     * GET /api/usuarios/rol/{rol} - Obtiene usuarios por rol
     */
    @GetMapping("/rol/{rol}")
    public ResponseEntity<List<UsuarioDTO>> obtenerPorRol(@PathVariable String rol) {
        List<UsuarioDTO> usuarios = usuarioService.obtenerPorRol(rol);
        return ResponseEntity.ok(usuarios);
    }

    /**
     * POST /api/usuarios - Crea un nuevo usuario
     */
    @PostMapping
    public ResponseEntity<UsuarioDTO> crear(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO usuarioCreado = usuarioService.crear(usuarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCreado);
    }

    /**
     * PUT /api/usuarios/{id} - Actualiza un usuario
     */
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO usuarioActualizado = usuarioService.actualizar(id, usuarioDTO);
        return ResponseEntity.ok(usuarioActualizado);
    }

    /**
     * DELETE /api/usuarios/{id} - Desactiva un usuario
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        usuarioService.desactivar(id);
        return ResponseEntity.noContent().build();
    }
}

/**
 * Controller de Autenticación (Auth).
 * Maneja login y registro de usuarios.
 */
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtConfig jwtConfig;

    /**
     * POST /api/auth/login - Autentica un usuario y retorna un token JWT
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(loginRequest.getEmail());

        if (usuarioOpt.isEmpty()) {
            throw new BadRequestException("Email o contraseña incorrectos");
        }

        Usuario usuario = usuarioOpt.get();

        if (!passwordEncoder.matches(loginRequest.getPassword(), usuario.getPassword())) {
            throw new BadRequestException("Email o contraseña incorrectos");
        }

        if (!usuario.getActivo()) {
            throw new BadRequestException("Usuario desactivado");
        }

        // Generar token JWT
        String token = jwtConfig.generateToken(usuario.getEmail(), usuario.getRol().toString());

        LoginResponseDTO response = new LoginResponseDTO();
        response.setToken(token);
        response.setEmail(usuario.getEmail());
        response.setNombre(usuario.getNombre());
        response.setApellido(usuario.getApellido());
        response.setRol(usuario.getRol().toString());

        return ResponseEntity.ok(response);
    }

    /**
     * POST /api/auth/register - Registra un nuevo usuario
     */
    @PostMapping("/register")
    public ResponseEntity<UsuarioDTO> register(@Valid @RequestBody RegisterRequestDTO registerRequest) {
        if (usuarioRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new BadRequestException("Email ya registrado");
        }

        Usuario nuevoUsuario = Usuario.builder()
                .nombre(registerRequest.getNombre())
                .apellido(registerRequest.getApellido())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .rol(Usuario.Rol.ESTUDIANTE) // Por defecto, ESTUDIANTE
                .activo(true)
                .build();

        Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);

        UsuarioDTO responseDTO = UsuarioDTO.builder()
                .id(usuarioGuardado.getId())
                .nombre(usuarioGuardado.getNombre())
                .apellido(usuarioGuardado.getApellido())
                .email(usuarioGuardado.getEmail())
                .rol(usuarioGuardado.getRol().toString())
                .activo(usuarioGuardado.getActivo())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
}

/**
 * DTO para login
 */
@Data
class LoginRequestDTO {
    private String email;
    private String password;
}

/**
 * DTO para respuesta de login
 */
@Data
@AllArgsConstructor
class LoginResponseDTO {
    private String token;
    private String email;
    private String nombre;
    private String apellido;
    private String rol;
    
    public LoginResponseDTO() {}
    
    public void setToken(String token) { this.token = token; }
    public void setEmail(String email) { this.email = email; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public void setRol(String rol) { this.rol = rol; }
}

/**
 * DTO para registro
 */
@Data
class RegisterRequestDTO {
    private String nombre;
    private String apellido;
    private String email;
    private String password;
}
