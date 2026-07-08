package cl.duocuc.sged.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

/**
 * Configuración de seguridad general del sistema.
 * Define qué endpoints son públicos y cuáles requieren autenticación/autorización.
 * 
 * Patrón: Portero (SecurityConfig) → Comprueba rol → Deja pasar o rechaza.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private CorsConfigurationSource corsConfigurationSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> authz
                        // Endpoints públicos
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
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
