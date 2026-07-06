package cl.duocuc.sged.exception;

/**
 * Excepción personalizada para recursos no encontrados.
 * Genera respuestas HTTP 404.
 */
public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
