package com.universidad.control_asistencia.controller;

import com.universidad.control_asistencia.service.IAService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/ia")
@RequiredArgsConstructor
public class IA_Controller {

    private final IAService iaService;

    @PostMapping("/chat")
    public ResponseEntity<Map<String, String>> ask(
            @RequestBody Map<String, String> body) {

        String mensaje = body.get("message");

        String respuesta = iaService.preguntar(mensaje);

        return ResponseEntity.ok(Map.of("response", respuesta));
    }
}
