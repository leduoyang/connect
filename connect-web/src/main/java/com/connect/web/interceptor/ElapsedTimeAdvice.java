package com.connect.web.interceptor;

import com.connect.api.common.APIResponse;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ElapsedTimeAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response
    ) {
        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
        Long startTime = (Long) servletRequest.getAttribute("startTime");

        if (startTime != null) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            if (body instanceof APIResponse) {
                APIResponse<?> apiResponse = (APIResponse<?>) body;
                apiResponse.setElapsedTime(elapsedTime + "ms");
            }
        }
        return body;
    }
}
