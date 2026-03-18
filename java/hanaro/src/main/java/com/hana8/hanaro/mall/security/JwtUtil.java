package com.hana8.hanaro.mall.security;

import com.hana8.hanaro.mall.security.exception.CustomJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    private Key key;

    @PostConstruct
    protected void init() {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Map<String, Object> valueMap, int min) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + (1000L * 60 * min));

        return Jwts.builder()
                .header().add("typ", "JWT").and()
                .claims(valueMap)
                .issuedAt(now)
                .expiration(validity)
                .signWith(key)
                .compact();
    }

    public Map<String, Object> validateToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith((SecretKey) key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new CustomJwtException("Expired");
        } catch (Exception e) {
            throw new CustomJwtException("Error");
        }
    }

    public Map<String, Object> authenticationToClaims(Authentication authentication) {
        String email = authentication.getName();
        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("ROLE_USER")
                .replace("ROLE_", "");

        return Map.of("email", email, "role", role);
    }
}
