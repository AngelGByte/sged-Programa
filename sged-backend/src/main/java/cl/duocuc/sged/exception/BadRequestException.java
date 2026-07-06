package cl.duocuc.sged.exception;

/**
 * Excepción personalizada para solicitudes con datos inválidos.
 * Genera respuestas HTTP 400.
 */
public class BadRequestException extends RuntimeException {
    
    public BadRequestException(String message) {
        super(message);
    }
    
    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
