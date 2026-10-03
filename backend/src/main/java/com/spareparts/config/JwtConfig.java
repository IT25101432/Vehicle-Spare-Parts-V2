package com.spareparts.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
    private String secret;
    private Long expirationTime;

    public String getSecret() { return secret; }
    public void setSecret(String secret) { this.secret = secret; }
    public Long getExpirationTime() { return expirationTime; }
    public void setExpirationTime(Long expirationTime) { this.expirationTime = expirationTime; }
}