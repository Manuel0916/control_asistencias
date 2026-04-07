package com.universidad.control_asistencia.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IAService {

    private ChatClient chatClient;

    @Value("${app.ai.negacion}")
    private String mensajeNegacion;


    public String preguntar(String mensaje, String chatId) {
        try {
            String respuesta = chatClient
                    .prompt()
                    .user(
                            mensaje.strip() // Quita multiples espacios en el inicio y en el fin
                                    .replaceAll("\\s+", " ") // Reduce múltiples espacios a uno
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