package hu.practice.formula_teams.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.file.AccessDeniedException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {
    private final JwtConfiguration jwtConfiguration;

    public JwtService(JwtConfiguration jwtConfiguration) {
        this.jwtConfiguration = jwtConfiguration;
    }

    public String generateToken(String username) {
        final Instant now = Instant.now();
        return Jwts.builder()
                .subject(username)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(Duration.ofMinutes(jwtConfiguration.getValidityMinutes()))))
                .signWith(getSecretKey())
                .compact();
    }

    public String extractUsername(String token) throws AccessDeniedException {
        return getTokenBody(token).getSubject();
    }

    public Boolean validateToken(String token, UserDetails userDetails) throws AccessDeniedException {
        final String username = extractUsername(token);
        return userDetails.getUsername().equals(username) && !isTokenExpired(token);
    }

    public String extractToken(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    private boolean isTokenExpired(String token) throws AccessDeniedException {
        final Date expiration = getTokenBody(token).getExpiration();
        return expiration.before(new Date());
    }

    private Claims getTokenBody(String token) throws AccessDeniedException {
        try {
            return Jwts.parser()
                       .verifyWith(getSecretKey())
                       .build()
                       .parseSignedClaims(token)
                       .getPayload();
        } catch (Exception e) {
            throw new AccessDeniedException("Access denied: " + e.getMessage());
        }
    }

    private SecretKey getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtConfiguration.getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

