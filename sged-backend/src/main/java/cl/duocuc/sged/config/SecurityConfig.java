package cl.duocuc.sged.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

/**
 * Configuración de seguridad general del sistema.
 * Define qué endpoints son públicos y cuáles requieren autenticación/autorización.
 * 
 * Patrón: Portero (SecurityConfig) → Comprueba rol → Deja pasar o rechaza.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private CorsConfigurationSource corsConfigurationSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests(authz -> authz
                        // Endpoints públicos
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
                        
                        // Health check
                        .requestMatchers("/actuator/health").permitAll()
                        
                        // Endpoints protegidos
                        .requestMatchers(HttpMethod.GET, "/api/usuarios/**").hasAnyRole("DOCENTE", "ADMINISTRADOR")
                        .requestMatchers(HttpMethod.GET, "/api/cursos/**").hasAnyRole("DOCENTE", "ESTUDIANTE", "ADMINISTRADOR")
                        .requestMatchers(HttpMethod.POST, "/api/cursos/**").hasAnyRole("DOCENTE", "ADMINISTRADOR")
                        .requestMatchers(HttpMethod.PUT, "/api/cursos/**").hasAnyRole("DOCENTE", "ADMINISTRADOR")
                        .requestMatchers(HttpMethod.GET, "/api/calificaciones/**").hasAnyRole("DOCENTE", "ESTUDIANTE", "ADMINISTRADOR")
                        .requestMatchers(HttpMethod.POST, "/api/calificaciones/**").hasAnyRole("DOCENTE", "ADMINISTRADOR")
                        .requestMatchers(HttpMethod.GET, "/api/dashboard/**").hasAnyRole("DOCENTE", "ADMINISTRADOR")
                        
                        // Cualquier otra petición requiere autenticación
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
