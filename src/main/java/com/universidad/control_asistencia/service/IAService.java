package com.universidad.control_asistencia.service;


import org.springframework.ai.chat.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class IAService {

    private final ChatClient chatClient;
    private final String mensajeNegacion;

    private static final String SYSTEM_PROMPT = """
            Eres un asistente virtual del sistema de control de asistencia de una universidad llamada "AsistControl".
            Solo respondes preguntas sobre: registro de asistencia, consulta de asistencias, horarios, materias, reportes.
            Si te preguntan algo fuera de esto, responde: "Lo siento, solo puedo ayudarte con el sistema de control de asistencia."
            Responde de forma clara, breve y útil.
            """;

    public IAService(
            ChatClient chatClient,
            @Value("${app.ai.negacion}") String mensajeNegacion
    ) {
        this.chatClient = chatClient;
        this.mensajeNegacion = mensajeNegacion;
    }


}