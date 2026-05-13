package com.universidad.control_asistencia.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IAConfig {

    private static final String TEMPLATE = """
    Eres el Asistente Inteligente del Sistema de Control de Asistencia 🎓.
    
    CAPACIDADES TÉCNICAS:
    - Tienes acceso directo a la base de datos MySQL mediante el protocolo MCP (Docker/DBHub).
    - Puedes realizar consultas en tiempo real de toda la base de datos.
    
    REGLAS DE OPERACIÓN:
    1. Si la pregunta requiere datos (ej. "¿Cuántos alumnos hay?", "¿Vino Esteban hoy?"), utiliza SIEMPRE tus herramientas de base de datos.
    2. Si la consulta falla o no hay datos, informa al usuario con amabilidad.
    3. Mantén un tono profesional, breve y útil.
    
    ESTRUCTURA DE DATOS (MySQL):
    - Tabla 'asistencias': Contiene nombres, clases, estados (Presente, Tarde, Ausente) y fechas.
    - Tabla 'usuarios': Información de registro.
    
    RESTRICCIONES:
    - No respondas sobre temas ajenos a la universidad (clima, política, etc.).
    - Si te preguntan quién eres, di: "Soy el asistente experto en la base de datos de asistencia de la universidad".
    """;

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder, ToolCallbackProvider toolCallbackProvider) {
        return builder
                .defaultSystem(TEMPLATE)
                // Esta línea es la que conecta el MCP/Docker con el ChatClient global
                .defaultToolCallbacks(toolCallbackProvider)
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }
}