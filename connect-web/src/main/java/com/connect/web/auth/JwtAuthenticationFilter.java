package com.connect.web.auth;

import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.web.util.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.Collections;

@Slf4j
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    public JwtAuthenticationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("enter jwt filter for " + request.getRequestURI());

        if (request.getRequestURI().startsWith("/api/connect/v1/public/") ||
                request.getRequestURI().equals("/api/root/test/token")
        ) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith(jwtTokenUtil.PREFIX)) {
            String userId = jwtTokenUtil.getUserIdFromToken(token);

            if (userId != null && !jwtTokenUtil.isTokenExpired(token)) {
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userId,
                        null,
                        Collections.singletonList(new SimpleGrantedAuthority(
                                jwtTokenUtil.getRoleFromToken(token)))
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("user: " + authentication.getName()
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