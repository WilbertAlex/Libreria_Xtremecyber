package libreria.com.infrestructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        System.out.println("🔍 Filtrando request: " + request.getRequestURI());

        // Obtener el header Authorization
        String authHeader = request.getHeader("Authorization");
        System.out.println("📋 Header Authorization: " + authHeader);

        String token = null;
        String username = null;

        // Verificar si el header contiene el token Bearer
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7); // Remover "Bearer "
            System.out.println("🎫 Token extraído: " + token.substring(0, Math.min(20, token.length())) + "...");

            try {
                username = jwtUtil.getUsernameFromToken(token);
                System.out.println("✅ Username extraído: " + username);
            } catch (Exception e) {
                System.out.println("❌ Error al extraer username del token: " + e.getMessage());
            }
        } else {
            System.out.println("⚠️ No se encontró token Bearer en el header");
        }

        // Si el token es válido y no hay autenticación previa
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            if (jwtUtil.validateToken(token)) {
                System.out.println("✅ Token válido, autenticando usuario: " + username);

                // Crear autenticación
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Establecer la autenticación en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                System.out.println("❌ Token inválido o expirado");
            }
        }

        filterChain.doFilter(request, response);
    }
}