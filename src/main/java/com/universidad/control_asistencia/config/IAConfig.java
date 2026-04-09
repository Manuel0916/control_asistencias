package com.universidad.control_asistencia.config;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IAConfig {

    @Value("${spring.ai.ollama.base-url}")
    private String baseUrl;

    @Value("${spring.ai.ollama.chat.options.model}")
    private String model;


}