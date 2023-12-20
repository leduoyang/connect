package com.connect.web.interceptor;

import com.connect.web.common.CustomHttpRequestWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.util.stream.Collectors;

@Log4j2
public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HttpServletRequest customRequestWrapper = new CustomHttpRequestWrapper(request);

        log.info("Request URI: " + customRequestWrapper.getRequestURI());
        log.info("Request Headers: " + customRequestWrapper.getHeaderNames());
        try {
            log.info("Request Body: " + customRequestWrapper.getReader().lines().collect(Collectors.joining()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        log.info("Response status: " + responseWrapper.getStatus());
        log.info("Response Headers: " + responseWrapper.getHeaderNames());
    }
}

