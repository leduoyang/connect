package com.connect.web.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HttpServletRequest wrappedRequest = new CustomHttpRequestWrapper(request);

        System.out.println("Request URI: " + wrappedRequest.getRequestURI());
        System.out.println("Request Headers: " + wrappedRequest.getHeaderNames());

        try {
            wrappedRequest.getReader().lines().forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
//        // Log response details
//        System.out.println("Response Status: " + response.getStatus());
//        System.out.println("Response Headers: " + response.getHeaderNames());
//        // Log payload (if any)
//        // Note: Reading the output stream here will consume it, so it should be wrapped if needed elsewhere.
//        System.out.println("Response Payload: " + getResponseBody(response));
//    }
}

