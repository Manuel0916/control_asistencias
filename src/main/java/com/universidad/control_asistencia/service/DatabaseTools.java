package com.universidad.control_asistencia.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

@Component
public class DatabaseTools {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseTools(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //  aca Definimos un registro (record) para que la IA pase el SQL correctamente
    public record SqlRequest(String sql) {}

    @Tool(description = "Ejecuta consultas SQL SELECT en la base de datos control_asistencia para ver alumnos y asistencias.")
    public String consultarBD(SqlRequest request) {
        // Limpiamos el SQL por si viene con basura
        String sql = request.sql().replace("\"", "").trim();

        // CORRECCIÓN AUTOMÁTICA: Si la IA olvida la 's', se la ponemos
        if (sql.toLowerCase().contains("from asistencia") && !sql.toLowerCase().contains("asistencias")) {
            sql = sql.toLowerCase().replace("from asistencia", "from asistencias");
        }

        System.out.println("🚀 EJECUTANDO SQL REAL EN MYSQL: " + sql);

        try {
            List<Map<String, Object>> resultados = jdbcTemplate.queryForList(sql);
            if (resultados.isEmpty()) {
                return "Base de datos conectada, pero no hay datos en esa tabla.";
            }
            return "Datos encontrados: " + resultados.toString();
        } catch (Exception e) {
            return "Error en la consulta: " + e.getMessage() + ". Asegúrate de usar nombres de tablas correctos como 'asistencias' o 'usuarios'.";
        }
    }
}