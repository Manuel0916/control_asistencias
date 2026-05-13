package com.universidad.control_asistencia.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ia")
@CrossOrigin(origins = "*")
public class IA_Controller {

    private final ChatClient chatClient;

    // Cambiamos IAService por ToolCallbackProvider para usar MCP/Docker
    public IA_Controller(ChatClient.Builder chatClientBuilder, ToolCallbackProvider toolCallbackProvider) {
        this.chatClient = chatClientBuilder
                .defaultToolCallbacks(toolCallbackProvider)
                .build();
    }

    @PostMapping("/chat")
    public ResponseEntity<Map<String, String>> chat(@RequestBody Map<String, String> request) {
        String mensaje = request.get("message");
        if (mensaje == null) mensaje = request.get("mensaje");

        if (mensaje == null || mensaje.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("response", "❌ Por favor, escribe una pregunta."));
        }

        try {
            // Aquí la IA usa automáticamente los servidores definidos en mcp-servers.json
            String respuesta = chatClient.prompt()
                    .user(mensaje)
                    .call()
                    .content();

            return ResponseEntity.ok(Map.of("response", respuesta));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body(Map.of("response", "⚠️ Error en el sistema MCP: " + e.getMessage()));
        }
    }
}