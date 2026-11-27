
package com.agrilink.auth.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
// Ce filtre vérifie le token JWT et récupère le rôle de l'utilisateur.
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. Récupérer l'en-tête Authorization.
        String authorizationHeader = request.getHeader("Authorization");

        // 2. Vérifier la présence du token Bearer.
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extraire le token.
        String token = authorizationHeader.substring(7);

        try {
            // 4. Récupérer l'email et le rôle depuis le token.
            String email = jwtService.extraireEmail(token);
            String role = jwtService.extraireClaims(token).get("role", String.class);

            // 5. Vérifier le token et les informations récupérées.
            if (email != null
                    && role != null
                    && jwtService.estValide(token)
                    && SecurityContextHolder.getContext().getAuthentication() == null) {

                // 6. Transformer le rôle en autorité Spring Security.
                SimpleGrantedAuthority authority =
                        new SimpleGrantedAuthority("ROLE_" + role);

                // 7. Créer l'authentification avec le rôle de l'utilisateur.
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                List.of(authority)
                        );

                // 8. Ajouter les détails de la requête.
                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // 9. Enregistrer l'utilisateur authentifié.
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        } catch (Exception e) {
            // Si le token est invalide, supprimer l'authentification.
            SecurityContextHolder.clearContext();
        }

        // 10. Continuer le traitement de la requête.
        filterChain.doFilter(request, response);
    }
}
