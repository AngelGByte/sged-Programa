package cl.duocuc.sged.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filtro que intercepta cada petición HTTP.
 * Lee el token JWT del header "Authorization", lo valida y deja pasar si es correcto.
 * 
 * Flujo: Cliente → Header Authorization: Bearer {token} → JwtAuthFilter valida → 
 *        Si válido: pasa a Controller. Si inválido: rechaza con 401.
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtConfig jwtConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String authHeader = request.getHeader("Authorization");
        
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String token = authHeader.substring(7);
                
                if (jwtConfig.validateToken(token)) {
                    String email = jwtConfig.getEmailFromToken(token);
                    String rol = jwtConfig.getRolFromToken(token);
                    
                    // Crear autenticación y establecer en el contexto
                    Authentication auth = new UsernamePasswordAuthenticationToken(
                            email, null, java.util.Collections.emptyList()
                    );
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"error\": \"Token inválido o expirado\"}");
                return;
            }
        }
        
        filterChain.doFilter(request, response);
    }
}
