package com.cinefamille.api.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // TODO 1 : lis le header "Authorization" depuis request.getHeader("Authorization")
        // Si absent (null) ou ne commence pas par "Bearer ", appelle filterChain.doFilter(request, response) et return
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // TODO 2 : extrait le token — c'est la partie du header après "Bearer "
        String authHeaderClean = authHeader.trim();
        String[] authHeaderParts = authHeaderClean.split(" ");
        String token = authHeaderParts[1];

        // TODO 3 : si le token est valide (jwtService.isTokenValid)
        // → extrais l'email avec jwtService.extractEmail
        // → crée un UsernamePasswordAuthenticationToken(email, null, List.of())
        // → enregistre-le : SecurityContextHolder.getContext().setAuthentication(...)
        if (jwtService.isTokenValid(token)) {
            String email = jwtService.extractEmail(token);
            UsernamePasswordAuthenticationToken authorizedToken =
                    new UsernamePasswordAuthenticationToken(email, null, List.of());
            SecurityContextHolder.getContext().setAuthentication(authorizedToken);
        }

        // TODO 4 : dans tous les cas, passe au filtre suivant
        filterChain.doFilter(request, response);
    }
}