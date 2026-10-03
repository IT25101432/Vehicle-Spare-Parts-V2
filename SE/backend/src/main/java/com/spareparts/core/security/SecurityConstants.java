package com.spareparts.core.security;

public class SecurityConstants {
    public static final String SECRET_KEY = "YourSuperSecretKeyForJwtGenerationMakeSureItsLongEnough"; // Ideally load from environment properties
    public static final long EXPIRATION_TIME = 86400000; // 24 Hours in milliseconds
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}