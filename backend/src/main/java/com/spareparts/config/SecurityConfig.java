package com.spareparts.config;

import com.spareparts.core.security.CustomUserDetailsService;
import com.spareparts.core.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, CustomUserDetailsService userDetailsService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
    }


    private final JwtAuthenticationFilter jwtAuthFilter;
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/auth/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        .requestMatchers("/api/categories", "/api/categories/**").hasAnyAuthority("ROLE_SYSTEM_ADMINISTRATOR", "ROLE_SHOP_OWNER", "ROLE_STORE_MANAGER", "ROLE_PROCUREMENT_OFFICER", "ROLE_STORE_KEEPER", "ROLE_SALES_STAFF", "ROLE_COMPATIBILITY_MANAGER")
                        .requestMatchers("/api/vehicles", "/api/vehicles/**").hasAnyAuthority("ROLE_SYSTEM_ADMINISTRATOR", "ROLE_SHOP_OWNER", "ROLE_STORE_MANAGER", "ROLE_STORE_KEEPER", "ROLE_SALES_STAFF", "ROLE_COMPATIBILITY_MANAGER")
                        .requestMatchers("/api/po", "/api/po/**", "/api/suppliers", "/api/suppliers/**").hasAnyAuthority("ROLE_SYSTEM_ADMINISTRATOR", "ROLE_SHOP_OWNER", "ROLE_STORE_MANAGER", "ROLE_PROCUREMENT_OFFICER")
                        .requestMatchers(HttpMethod.GET, "/api/parts", "/api/parts/**").hasAnyAuthority("ROLE_SYSTEM_ADMINISTRATOR", "ROLE_SHOP_OWNER", "ROLE_STORE_MANAGER", "ROLE_STORE_KEEPER", "ROLE_SALES_STAFF", "ROLE_PROCUREMENT_OFFICER")
                        .requestMatchers("/api/parts", "/api/parts/**").hasAnyAuthority("ROLE_SYSTEM_ADMINISTRATOR", "ROLE_SHOP_OWNER", "ROLE_STORE_MANAGER", "ROLE_STORE_KEEPER", "ROLE_SALES_STAFF")
                        .requestMatchers("/api/sales", "/api/sales/**").hasAnyAuthority("ROLE_SYSTEM_ADMINISTRATOR", "ROLE_SHOP_OWNER", "ROLE_STORE_MANAGER", "ROLE_SALES_STAFF")
                        .requestMatchers("/api/mappings", "/api/mappings/**").hasAnyAuthority("ROLE_SYSTEM_ADMINISTRATOR", "ROLE_SHOP_OWNER", "ROLE_STORE_MANAGER", "ROLE_COMPATIBILITY_MANAGER")
                        .requestMatchers("/api/users", "/api/users/**", "/api/analytics", "/api/analytics/**", "/api/reports", "/api/reports/**").hasAnyAuthority("ROLE_SYSTEM_ADMINISTRATOR", "ROLE_SHOP_OWNER", "ROLE_STORE_MANAGER")
                        .requestMatchers("/api/alerts").hasAnyAuthority("ROLE_SYSTEM_ADMINISTRATOR", "ROLE_SHOP_OWNER", "ROLE_STORE_MANAGER", "ROLE_STORE_KEEPER", "ROLE_SALES_STAFF", "ROLE_PROCUREMENT_OFFICER")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
