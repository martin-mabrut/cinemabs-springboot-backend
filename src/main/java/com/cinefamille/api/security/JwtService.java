package com.cinefamille.api.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

@Service
public class JwtService {

    // La clé secrète utilisée pour signer les tokens.
    @Value("${jwt.secret}")
    private String secret;

    // Durée de validité du token : 24 heures en millisecondes
    private static final long EXPIRATION_MS = 1000 * 60 * 60 * 24;

    // Construit un objet SecretKey à partir de la chaîne SECRET
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * Génère un token JWT pour un utilisateur donné.
     * Le "subject" du token est l'email de l'utilisateur.
     *
     * Exemple de résultat : "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQG1haWwuY29tIn0.abc123"
     */
    public String generateToken(String email) {
        return Jwts.builder()
            .subject(email)
                // identifiant de l'utilisateur stocké dans le token
            .issuedAt(new Date())
                // date de création du token
            .expiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                // date d'expiration (maintenant + 24h)
            .signWith(getSigningKey())
                // signe le token avec la clé secrète
            .compact();
                // génère la chaîne JWT finale
    }

    /**
     * Extrait l'email (subject) contenu dans un token JWT.
     *
     * Exemple : extractEmail("eyJhbGci...") → "user@mail.com"
     */
    public String extractEmail(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                    // vérifie que la signature du token correspond à la clé secrète
                .build()
                    // construit le parser avec la configuration
                .parseSignedClaims(token)
                    // lit et décode le token JWT
                .getPayload()
                    // accède au contenu (subject, dates...)
                .getSubject();
                    // extrait le subject (= l'email de l'utilisateur)
    }

    /**
     * Vérifie si un token est valide (signature correcte + non expiré).
     */
    public boolean isTokenValid(String token) {
        try {
            extractEmail(token); // Lance une exception si le token est invalide ou expiré
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
