package cl.duocuc.sged.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) para crear/actualizar usuarios.
 * Separa la estructura de datos que entra de la entidad JPA.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDTO {
    
    private Long id;
    
    @NotBlank(message = "El nombre es requerido")
    private String nombre;
    
    @NotBlank(message = "El apellido es requerido")
    private String apellido;
    
    @Email(message = "Email debe ser válido")
    @NotBlank(message = "El email es requerido")
    private String email;
    
    @NotBlank(message = "El rol es requerido")
    private String rol;
    
    private Boolean activo;
}

/**
 * DTO para respuestas de autenticación.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class LoginRequestDTO {
    
    @Email(message = "Email debe ser válido")
    @NotBlank(message = "El email es requerido")
    private String email;
    
    @NotBlank(message = "La contraseña es requerida")
    private String password;
}

/**
 * DTO para respuestas de login exitoso.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class LoginResponseDTO {
    private String token;
    private String email;
    private String nombre;
    private String apellido;
    private String rol;
}
