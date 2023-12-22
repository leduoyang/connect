package com.connect.web.config;

import com.connect.web.filter.TraceIdFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<TraceIdFilter> traceFilter() {
        FilterRegistrationBean<TraceIdFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setOrder(1);
        registrationBean.setFilter(new TraceIdFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
