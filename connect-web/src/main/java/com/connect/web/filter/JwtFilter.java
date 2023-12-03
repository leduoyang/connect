package com.connect.web.filter;

import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.web.util.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (request.getRequestURI().equals("**/user/signup") ||
            request.getMethod().equals("GET") ||
            (request.getHeader("isRoot") != null && request.getHeader("isRoot").equals("true"))
        ) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            String token = authorization.substring(7);
            String userId = jwtTokenUtil.getUserIdFromToken(token);

            if (userId != null && !jwtTokenUtil.isTokenExpired(token)) {
                log.warn("user verified: " + userId);
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

