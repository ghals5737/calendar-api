package com.example.calendar.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/api/**")
//                .allowedOrigins("*")
//                .allowedMethods("POST", "GET", "PUT", "DELETE","OPTIONS")
//                .allowedHeaders("Content-Type", "Authorization", "X-Requested-With", "Custom-Header")
//                .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Methods", "Access-Control-Allow-Headers", "Access-Control-Max-Age")
//                .allowCredentials(true)
//                .maxAge(3600);
//    }
}
