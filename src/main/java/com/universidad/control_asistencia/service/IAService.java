package com.universidad.control_asistencia.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class IAService {

    private final ChatClient chatClient;

    @Value("${app.ai.negacion}")
    private String mensajeNegacion;

    // Contructor por defecto
    public IAService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String OllamaAsk(String mensaje) {
        try {
            String respuesta = chatClient
                    .prompt()
                    .user(mensaje)
                    .call()
                    .content();
            if (respuesta == null || respuesta.isBlank()) {
                return mensajeNegacion;
            }
            return respuesta;
        } catch (Exception e) {
            return "Error al conectar con la IA. Intenta nuevamente.";
        }
    }

}