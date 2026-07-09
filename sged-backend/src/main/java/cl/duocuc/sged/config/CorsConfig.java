package cl.duocuc.sged.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Configuración CORS (Cross-Origin Resource Sharing).
 * Permite que el frontend React (en otro puerto) pueda hacer peticiones al backend.
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Orígenes permitidos (frontend)
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:5173",    // Vite dev server
                "http://localhost:5174",    // Vite dev server (alternative port)
                "http://localhost:3000",     // Alternative frontend
                "http://127.0.0.1:5173",
                "http://127.0.0.1:5174"
        ));
        
        // Métodos HTTP permitidos
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        
        // Headers permitidos
        configuration.setAllowedHeaders(Arrays.asList("*"));
        
        // Credenciales
        configuration.setAllowCredentials(true);
        
        // Tiempo máximo de cache (segundos)
        configuration.setMaxAge(3600L);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }
}
