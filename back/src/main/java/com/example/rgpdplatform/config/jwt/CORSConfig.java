package com.example.rgpdplatform.config.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class CORSConfig {

    @Value("#{'${rgpd.saas.cors.cross-origins}'.split(',')}")
    private String[] CROSS_ORIGINS;
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/**")
                        .allowedOrigins(CROSS_ORIGINS)
                        .allowedMethods("*")
                        .allowCredentials(true);
            }
        };
    }
}
