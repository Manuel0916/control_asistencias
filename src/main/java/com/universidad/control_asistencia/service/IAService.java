package com.universidad.control_asistencia.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IAService {

    private final ChatClient chatClient;

    @Value("${app.ai.negacion}")
    private String mensajeNegacion;

    public String preguntar(String mensaje) {
        try {
            String respuesta = chatClient
                    .prompt()
                    .user(
                            mensaje.strip()
                                    .replaceAll("\\s+", " ")
                    )
                    .call()
                    .content();

            if (respuesta == null || respuesta.isBlank()) {
                return mensajeNegacion;
            }

            return respuesta;

        } catch (Exception e) {
            return "Error al conectar con la IA";
        }
    }
}