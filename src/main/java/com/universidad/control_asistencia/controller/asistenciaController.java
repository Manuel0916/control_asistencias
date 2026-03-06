package com.universidad.control_asistencia.controller;

import com.universidad.control_asistencia.interfaces.AsistenciaInterface;
import com.universidad.control_asistencia.model.Asistencia;
import com.universidad.control_asistencia.model.Usuario;
import com.universidad.control_asistencia.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/asistencias")
@CrossOrigin(origins = "*")
public class asistenciaController {

    @Autowired
    private AsistenciaInterface asistenciaInterface;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Registrar asistencia desde QR
    @PostMapping
    public ResponseEntity<?> registrarAsistencia(@RequestBody Map<String, Object> payload, Authentication authentication) {
        Map<String, Object> response = new HashMap<>();

        try {
            System.out.println("=== REGISTRANDO ASISTENCIA DESDE QR ===");
            System.out.println("Payload recibido: " + payload);

            // Verificar autenticación
            if (authentication == null || !authentication.isAuthenticated()) {
                System.out.println("❌ Usuario no autenticado");
                response.put("error", "Usuario no autenticado");
                return ResponseEntity.status(401).body(response);
            }

            // Validar que la clase existe
            String clase = (String) payload.get("clase");
            if (clase == null || clase.trim().isEmpty()) {
                System.out.println("❌ Clase no proporcionada");
                response.put("error", "La clase es requerida en el código QR");
                return ResponseEntity.badRequest().body(response);
            }

            // Obtener usuario autenticado
            String username = authentication.getName();
            System.out.println("Usuario autenticado: " + username);

            Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);
            if (usuarioOpt.isEmpty()) {
                usuarioOpt = usuarioRepository.findByEmail(username);
            }

            if (usuarioOpt.isEmpty()) {
                System.out.println("❌ Usuario no encontrado: " + username);
                response.put("error", "Usuario no encontrado en la base de datos");
                return ResponseEntity.status(404).body(response);
            }

            Usuario usuario = usuarioOpt.get();
            System.out.println("✅ Usuario encontrado: " + usuario.getUsername());

            // Crear asistencia
            Asistencia asistencia = new Asistencia();
            asistencia.setClase(clase.trim());
            asistencia.setNombre(usuario.getUsername());
            asistencia.setEstado("Presente");

            // Manejar fecha
            try {
                if (payload.containsKey("timestamp") && payload.get("timestamp") != null) {
                    String timestampStr = (String) payload.get("timestamp");
                    LocalDateTime timestamp = LocalDateTime.parse(timestampStr, DateTimeFormatter.ISO_DATE_TIME);
                    asistencia.setFechaHora(timestamp);
                } else {
                    asistencia.setFechaHora(LocalDateTime.now());
                }
            } catch (DateTimeParseException e) {
                System.out.println("⚠️ Error parseando timestamp, usando fecha actual");
                asistencia.setFechaHora(LocalDateTime.now());
            }

            System.out.println("📝 Guardando asistencia:");
            System.out.println("   - Nombre: " + asistencia.getNombre());
            System.out.println("   - Clase: " + asistencia.getClase());
            System.out.println("   - Fecha: " + asistencia.getFechaHora());

            // Guardar
            Asistencia guardada = asistenciaInterface.registrarAsistencia(asistencia);

            System.out.println("✅ Asistencia guardada con ID: " + guardada.getId());

            response.put("mensaje", "Asistencia registrada correctamente");
            response.put("id", guardada.getId());
            response.put("nombre", guardada.getNombre());
            response.put("clase", guardada.getClase());
            response.put("fechaHora", guardada.getFechaHora().toString());
            response.put("estado", guardada.getEstado());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("❌ Error inesperado:");
            e.printStackTrace();
            response.put("error", "Error interno del servidor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Registrar asistencia manual
    @PostMapping("/manual")
    public ResponseEntity<?> registrarAsistenciaManual(@RequestBody Asistencia asistencia, Authentication authentication) {
        Map<String, Object> response = new HashMap<>();

        try {
            System.out.println("=== REGISTRANDO ASISTENCIA MANUAL ===");

            if (asistencia.getNombre() == null || asistencia.getNombre().trim().isEmpty()) {
                response.put("error", "El nombre del estudiante es requerido");
                return ResponseEntity.badRequest().body(response);
            }

            if (asistencia.getClase() == null || asistencia.getClase().trim().isEmpty()) {
                response.put("error", "La clase es requerida");
                return ResponseEntity.badRequest().body(response);
            }

            if (asistencia.getEstado() == null || asistencia.getEstado().trim().isEmpty()) {
                asistencia.setEstado("Presente");
            }

            if (asistencia.getFechaHora() == null) {
                asistencia.setFechaHora(LocalDateTime.now());
            }

            System.out.println("📝 Datos: " + asistencia.getNombre() + " - " + asistencia.getClase());

            Asistencia guardada = asistenciaInterface.registrarAsistencia(asistencia);

            response.put("mensaje", "Asistencia registrada correctamente");
            response.put("id", guardada.getId());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Listar todas
    @GetMapping
    public ResponseEntity<?> listarAsistencias() {
        try {
            List<Asistencia> asistencias = asistenciaInterface.obtenerTodas();
            return ResponseEntity.ok(asistencias);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    // Por clase
    @GetMapping("/clase/{clase}")
    public ResponseEntity<?> obtenerAsistenciasPorClase(@PathVariable String clase) {
        try {
            List<Asistencia> asistencias = asistenciaInterface.obtenerPorClase(clase);
            return ResponseEntity.ok(asistencias);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    // Por nombre
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<?> obtenerAsistenciasPorNombre(@PathVariable String nombre) {
        try {
            System.out.println("=== BUSCANDO ASISTENCIAS PARA: " + nombre);
            List<Asistencia> asistencias = asistenciaInterface.obtenerPorNombre(nombre);
            System.out.println("✅ Encontradas: " + asistencias.size());
            return ResponseEntity.ok(asistencias);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    // Por rango de fechas
    @GetMapping("/rango")
    public ResponseEntity<?> obtenerAsistenciasPorRangoDeFechas(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        Map<String, Object> response = new HashMap<>();

        try {
            LocalDateTime start = LocalDateTime.parse(startDate);
            LocalDateTime end = LocalDateTime.parse(endDate);

            List<Asistencia> asistencias = asistenciaInterface.obtenerPorRangoDeFechas(start, end);
            return ResponseEntity.ok(asistencias);

        } catch (DateTimeParseException e) {
            response.put("error", "Formato de fecha inválido");
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    // Mis asistencias (estudiante actual)
    @GetMapping("/mis-asistencias")
    public ResponseEntity<?> obtenerMisAsistencias(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();

        try {
            System.out.println("=== OBTENIENDO MIS ASISTENCIAS ===");

            if (authentication == null || !authentication.isAuthenticated()) {
                response.put("error", "No autenticado");
                return ResponseEntity.status(401).body(response);
            }

            String username = authentication.getName();
            System.out.println("Usuario: " + username);

            Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);
            if (usuarioOpt.isEmpty()) {
                usuarioOpt = usuarioRepository.findByEmail(username);
            }

            if (usuarioOpt.isEmpty()) {
                response.put("error", "Usuario no encontrado");
                return ResponseEntity.status(404).body(response);
            }

            String nombreEstudiante = usuarioOpt.get().getUsername();
            List<Asistencia> asistencias = asistenciaInterface.obtenerPorNombre(nombreEstudiante);

            System.out.println("✅ Asistencias encontradas: " + asistencias.size());

            return ResponseEntity.ok(asistencias);

        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }


    }
