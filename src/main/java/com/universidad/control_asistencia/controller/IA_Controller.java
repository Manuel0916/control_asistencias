package com.universidad.control_asistencia.controller;

import com.universidad.control_asistencia.service.IAService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/ia")
public class IA_Controller {

    private final IAService iaService;

    public IA_Controller(IAService iaService) {
        this.iaService = iaService;
    }

    @PostMapping("/chat")
    public ResponseEntity<Map<String, String>> chat(@RequestBody Map<String, String> request) {

        String mensaje = request.get("message");

        if (mensaje == null || mensaje.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("response", "❌ El mensaje no puede estar vacío"));
        }

        String respuesta = iaService.preguntar(mensaje);

        return ResponseEntity.ok(Map.of("response", respuesta));
    }
}