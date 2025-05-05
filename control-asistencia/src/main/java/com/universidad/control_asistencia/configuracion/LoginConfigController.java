package com.universidad.control_asistencia.configuracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login-config")
public class LoginConfigController {

    @Autowired
    private LoginConfigService loginConfigService;

    // Endpoint para verificar si el login está habilitado para un rol
    @GetMapping("/{rol}/habilitado")
    public boolean isLoginEnabled(@PathVariable String rol) {
        return loginConfigService.isLoginHabilitado(rol);
    }

    // Endpoint para cambiar el estado del login (habilitado/deshabilitado)
    @PutMapping("/{rol}/toggle")
    public ResponseEntity<LoginConfig> toggleLoginState(@PathVariable String rol) {
        try {
            LoginConfig updatedConfig = loginConfigService.cambiarEstadoLogin(rol);
            return ResponseEntity.ok(updatedConfig);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // Error en el cambio de estado
        }
    }
}
