package com.universidad.control_asistencia.service;

@Service
@RequiredArgsConstructor
public class IAService {

    private final ChatClient chatClient;

    @Value("${app.ai.negacion-respuesta}")
    private String mensajeNegacion;

    @Override
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