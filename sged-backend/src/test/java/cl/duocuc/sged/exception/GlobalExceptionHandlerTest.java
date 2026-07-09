package cl.duocuc.sged.exception;

import cl.duocuc.sged.usuario.dto.UsuarioDTO;
import jakarta.validation.Valid;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.lang.reflect.Method;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleResourceNotFound_returns404Response() {
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response =
                handler.handleResourceNotFound(new ResourceNotFoundException("Usuario no encontrado"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getBody().getError()).isEqualTo("Recurso no encontrado");
        assertThat(response.getBody().getMessage()).isEqualTo("Usuario no encontrado");
    }

    @Test
    void handleBadRequest_returns400Response() {
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response =
                handler.handleBadRequest(new BadRequestException("Email ya registrado"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getBody().getError()).isEqualTo("Solicitud inválida");
        assertThat(response.getBody().getMessage()).isEqualTo("Email ya registrado");
    }

    @Test
    void handleValidationExceptions_returnsValidationErrorsMap() throws Exception {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new UsuarioDTO(), "usuarioDTO");
        bindingResult.addError(new FieldError("usuarioDTO", "email", "Email inválido"));

        MethodParameter methodParameter = new MethodParameter(
                TestController.class.getDeclaredMethod("createUsuario", UsuarioDTO.class), 0);
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(methodParameter, bindingResult);

        ResponseEntity<Map<String, Object>> response = handler.handleValidationExceptions(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).containsEntry("status", HttpStatus.BAD_REQUEST.value());
        assertThat(response.getBody()).containsEntry("error", "Errores de validación");
        assertThat(response.getBody()).containsKey("timestamp");
        assertThat(response.getBody()).containsEntry("validationErrors", Map.of("email", "Email inválido"));
    }

    @Test
    void handleGenericException_returns500Response() {
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response =
                handler.handleGenericException(new RuntimeException("boom"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getBody().getError()).isEqualTo("Error interno del servidor");
        assertThat(response.getBody().getMessage())
                .isEqualTo("Ha ocurrido un error inesperado. Por favor contacte al administrador.");
    }

    static class TestController {
        @SuppressWarnings("unused")
        void createUsuario(@Valid UsuarioDTO usuarioDTO) {
        }
    }
}
