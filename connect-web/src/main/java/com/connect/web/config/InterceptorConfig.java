package com.connect.web.config;

import com.connect.web.interceptor.ElapsedTimeInterceptor;
import com.connect.web.interceptor.LoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    public LoggingInterceptor loggingInterceptor() {
        return new LoggingInterceptor();
    }

    @Bean
    public ElapsedTimeInterceptor elapsedTimeInterceptor() {
        return new ElapsedTimeInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor());
        registry.addInterceptor(elapsedTimeInterceptor());
    }
}
