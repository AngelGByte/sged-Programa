package cl.duocuc.sged.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filtro que intercepta cada petición HTTP.
 * Lee el token JWT del header "Authorization", lo valida y deja pasar si es correcto.
 * 
 * Flujo: Cliente → Header Authorization: Bearer {token} → JwtAuthFilter valida → 
 *        Si válido: pasa a Controller. Si inválido: rechaza con 401.
 */
@Component
public class JwtAuthFilter implements Filter {

    @Autowired
    private JwtConfig jwtConfig;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        // Obtener el header Authorization
        String authHeader = httpRequest.getHeader("Authorization");
        
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            
            if (!jwtConfig.validateToken(token)) {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.getWriter().write("{\"error\": \"Token inválido o expirado\"}");
                return;
            }
        }
        
        chain.doFilter(request, response);
    }
}
