package com.capstone.timepay.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "http://13.125.249.51",
                        "http://54.180.107.93", "http://localhost:3001")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedMethods("*");
    }
}
