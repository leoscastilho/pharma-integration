package com.estee.na.config;

import com.estee.na.service.interceptor.ServiceContextInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan("com.estee.na")
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private ServiceContextInterceptor serviceContextInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {

        interceptorRegistry.addInterceptor(serviceContextInterceptor);

    }
}
