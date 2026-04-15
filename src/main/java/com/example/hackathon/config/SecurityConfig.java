package com.example.hackathon.config;

import com.example.hackathon.model.RolUsuario;
import com.example.hackathon.repository.UsuarioRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig {

    private final UsuarioRepository usuarioRepository;
    private final RequestCache requestCache = new HttpSessionRequestCache();

    public SecurityConfig(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(
                    HttpServletRequest request,
                    HttpServletResponse response,
                    org.springframework.security.core.Authentication authentication
            ) throws IOException, ServletException {
                String email = authentication.getName();
                RolUsuario rol = usuarioRepository.findByEmail(email)
                        .map(usuario -> usuario.getRol())
                        .orElse(null);

                SavedRequest savedRequest = requestCache.getRequest(request, response);
                String savedUrl = savedRequest != null ? savedRequest.getRedirectUrl() : null;

                if (rol == RolUsuario.BENEFICIARIO && savedUrl != null && savedUrl.contains("/beneficiario/dashboard")) {
                    response.sendRedirect(savedUrl);
                    return;
                }

                if (rol == RolUsuario.DONADOR) {
                    response.sendRedirect("/donador/dashboard");
                    return;
                }

                if (rol == RolUsuario.BENEFICIARIO) {
                    response.sendRedirect("/beneficiario/dashboard");
                    return;
                }

                response.sendRedirect("/");
            }
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/", "/login", "/registro", "/acceso-denegado", "/ejemplo",
                                "/publicar", "/donador", "/beneficiario", "/auth/registro"
                        ).permitAll()
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/h2-console/**").permitAll()
                        .requestMatchers("/api/donaciones/**", "/donaciones/**").permitAll()
                        .requestMatchers("/donador/dashboard").hasRole("DONADOR")
                        .requestMatchers("/beneficiario/dashboard").hasRole("BENEFICIARIO")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .successHandler(authenticationSuccessHandler())
                        .failureUrl("/login?error")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }
}
