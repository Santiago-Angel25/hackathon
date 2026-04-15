package com.example.hackathon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                    .disable()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                    // Rutas públicas - Acceso sin autenticación
                    .requestMatchers("/", "/login", "/registro", "/acceso-denegado", "/ejemplo").permitAll()
                    .requestMatchers("/donaciones", "/donaciones/**").permitAll()
                    .requestMatchers("/swagger-ui.html", "/v3/api-docs", "/v3/api-docs/**").permitAll()
                    .requestMatchers("/css/**", "/js/**", "/static/**").permitAll()
                    // Admin - Requiere rol ADMIN
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    // Auth endpoints
                    .requestMatchers("/auth/**").permitAll()
                    // Cualquier otra ruta requiere autenticación
                    .anyRequest().authenticated()
                .and()
                .httpBasic();

        return http.build();
    }
}

