package com.universidad.control_asistencia.service;

import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class IAService {

    private final OllamaChatClient chatClient;
    private final String mensajeNegacion;

    public IAService(
            OllamaChatClient chatClient,
            @Value("${app.ai.negacion}") String mensajeNegacion
    ) {
        this.chatClient = chatClient;
        this.mensajeNegacion = mensajeNegacion;
    }

    public String preguntar(String mensaje) {
        try {
            if (mensaje == null || mensaje.isBlank()) {
                return "Por favor, escribe un mensaje válido.";
            }

            String mensajeLimpio = mensaje.strip().replaceAll("\\s+", " ");

            String respuesta = chatClient.call(mensajeLimpio);

            if (respuesta == null || respuesta.isBlank()) {
                return mensajeNegacion;
            }

            return respuesta;

        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Error al conectar con la IA local (Ollama). Verifica que Ollama esté corriendo en http://localhost:11434";
        }
    }
}