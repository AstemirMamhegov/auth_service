package com.example.authservice.security;

import com.example.authservice.dto.AuthResponse;
import com.example.authservice.entity.User;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import java.security.Key;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtProvider {

    private final Key key;
    private final JwtParser parser;
    private final JwtProperties props;


    public JwtProvider(JwtProperties props) {
        this.props = props;
        this.key = Keys.hmacShaKeyFor(props.getSecret().getBytes());
        this.parser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
    }

    public AuthResponse generateTokens(User user) {
        String accessToken = Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("email", user.getEmail())
                .claim("roles", user.getRoles().stream()
                        .map(role -> role.getName().name())
                        .toList())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + props.getAccessTokenExpiration()))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + props.getRefreshTokenExpiration()))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return new AuthResponse(accessToken, refreshToken);
    }

    public boolean validate(String token) {
        try {
            parser.parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public UUID getUserIdFromToken(String token) {
        Claims claims = parser.parseClaimsJws(token).getBody();

        return UUID.fromString(claims.getSubject());
    }

    public AuthResponse refreshTokens(String refreshToken) {
        if (!validate(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }
        UUID userId = getUserIdFromToken(refreshToken);
        User dummy = new User();
        dummy.setId(userId);
        dummy.setEmail("placeholder@example.com");
        return generateTokens(dummy);
    }

    public Claims getClaims(String token) {
        return parser.parseClaimsJws(token).getBody();
    }
}
