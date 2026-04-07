package com.universidad.control_asistencia.controller;

import com.universidad.control_asistencia.service.IAService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/ia")
@AllArgsConstructor
public class IA_Controller {

    private final IAService iaService;

    @PostMapping("/chat")
    public ResponseEntity<Map<String, String>> chat(@RequestBody Map<String, String> request) {

        String mensaje = request.get("message");

        if (mensaje == null || mensaje.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("response", "Mensaje vacío"));
        }

        String respuesta = iaService.preguntar(mensaje);

        return ResponseEntity.ok(Map.of("response", respuesta));
    }
}