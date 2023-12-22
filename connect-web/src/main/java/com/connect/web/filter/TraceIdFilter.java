package com.connect.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class TraceIdFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        String traceId = MDC.get("traceId");
        if (traceId == null) {
            if (request.getHeader("traceId") != null) {
                traceId = request.getHeader("traceId");
            } else {
                traceId = UUID.randomUUID().toString();
            }
            MDC.put("traceId", traceId);
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            MDC.remove("traceId");
        }
    }

    @Override
    public void destroy() {
    }
}

