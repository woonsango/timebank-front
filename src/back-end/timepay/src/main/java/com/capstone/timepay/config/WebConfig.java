package com.capstone.timepay.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://*.cs.kookmin.ac.kr",
                        "https://timepay.cs.kookmin.ac.kr", 
                        "https://timebank.cs.kookmin.ac.kr")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedMethods("*");
    }
}
