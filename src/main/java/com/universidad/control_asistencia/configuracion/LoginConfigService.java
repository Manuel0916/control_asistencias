package com.universidad.control_asistencia.configuracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginConfigService {

    @Autowired
    private LoginConfigRepository repository;

    // Método para comprobar si el login está habilitado para un rol
    public boolean isLoginHabilitado(String rol) {
        return repository.findByRol(rol)
                .map(LoginConfig::isLoginHabilitado)
                .orElse(true); // Si no hay registro, por defecto habilitado
    }

    // Método para cambiar el estado del login (habilitado/deshabilitado)
    public LoginConfig cambiarEstadoLogin(String rol) {
        LoginConfig config = repository.findByRol(rol).orElse(new LoginConfig(rol, true));
        config.setLoginHabilitado(!config.isLoginHabilitado());
        return repository.save(config);
    }

    // Obtener el estado actual del login de un rol
    public LoginConfig obtenerEstado(String rol) {
        return repository.findByRol(rol).orElse(new LoginConfig(rol, true));
    }
}
