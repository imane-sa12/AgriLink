package com.agrilink.auth.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.expiration-minutes:120}")
    private long expirationMinutes;

    @Value("${app.jwt.issuer:agrilink}")
    private String issuer;

    // Générer la clé utilisée pour signer et vérifier les tokens
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(
            secret.getBytes(StandardCharsets.UTF_8)
        );
    }

    // Générer un token JWT
    public String genererToken(Long idUtilisateur,
                               String email,
                               String role) {

        Date maintenant = new Date();

        Date expiration = new Date(
            maintenant.getTime() + expirationMinutes * 60 * 1000
        );

        return Jwts.builder()
                .subject(email)
                .claim("idUtilisateur", idUtilisateur)
                .claim("role", role)
                .issuer(issuer)
                .issuedAt(maintenant)
                .expiration(expiration)
                .signWith(getSigningKey())
                .compact();
    }

    // Extraire les informations du token
    public Claims extraireClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Extraire l'email du token
    public String extraireEmail(String token) {
        return extraireClaims(token).getSubject();
    }

    // Vérifier si le token est valide
    public boolean estValide(String token) {

        try {
            Claims claims = extraireClaims(token);

            return claims.getExpiration().after(new Date())
                    && issuer.equals(claims.getIssuer());

        } catch (Exception e) {
            return false;
        }
    }
}
