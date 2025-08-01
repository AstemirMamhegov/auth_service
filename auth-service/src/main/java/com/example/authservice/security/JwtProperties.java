package com.example.authservice.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String secret;
    private long accessTokenExpiration;
    private long refreshTokenExpiration;

    public String getSecret() { return secret; }
    public long getAccessTokenExpiration() { return accessTokenExpiration; }
    public long getRefreshTokenExpiration() {return refreshTokenExpiration;}
}
