package com.example.hackathon.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class AppRestartSessionFilter extends OncePerRequestFilter {

    private static final String APP_INSTANCE_KEY = "appInstanceId";
    private final String currentAppInstanceId = UUID.randomUUID().toString();

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean autenticado = authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken);

        if (autenticado) {
            HttpSession session = request.getSession(false);

            if (session != null) {
                Object sessionAppInstance = session.getAttribute(APP_INSTANCE_KEY);

                if (sessionAppInstance != null && !currentAppInstanceId.equals(sessionAppInstance.toString())) {
                    session.invalidate();
                    SecurityContextHolder.clearContext();
                    response.sendRedirect("/login?restart");
                    return;
                }

                session.setAttribute(APP_INSTANCE_KEY, currentAppInstanceId);
            }
        }

        filterChain.doFilter(request, response);
    }
}
