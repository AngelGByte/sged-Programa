package cl.duocuc.sged;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada de la aplicación SGED.
 * Sistema Integral de Gestión Académica y Bienestar Estudiantil
 * 
 * Monolito modular con las siguientes características:
 * - Autenticación y autorización con JWT
 * - Gestión de usuarios, cursos, calificaciones y asistencias
 * - Dashboard con reportes académicos
 * - CORS configurado para comunicación con frontend React
 */
@SpringBootApplication
public class SgedApplication {

    public static void main(String[] args) {
        SpringApplication.run(SgedApplication.class, args);
    }

}
