package com.connect.web.filter;

import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.web.util.JwtAuthenticationUtil;
import com.connect.web.util.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    public JwtAuthenticationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Autowired
    private JwtAuthenticationUtil jwtAuthenticationUtil;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("enter jwt filter for " + request.getRequestURI());

        if (request.getRequestURI().startsWith("/api/connect/v1/public/") ||
                request.getRequestURI().equals("/api/root/test/token") ||
                request.getMethod().equals("GET")
        ) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith(jwtTokenUtil.PREFIX)) {
            String userId = jwtTokenUtil.getUserIdFromToken(token);

            if (userId != null && !jwtTokenUtil.isTokenExpired(token)) {
                Authentication authentication = jwtAuthenticationUtil.createAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info(SecurityContextHolder.getContext().getAuthentication().toString());
                log.info("user: " + userId
                        + ", is authenticated: "
                        + authentication.isAuthenticated()
                        + ", has role: "
                        + authentication.getAuthorities()
                );

                this.getAuthenticationManager().authenticate(authentication);
                filterChain.doFilter(request, response);
                return;
            }
        }

        throw new ConnectDataException(
                ConnectErrorCode.UNAUTHORIZED_EXCEPTION,
                "unauthorized request found"
        );
    }
}