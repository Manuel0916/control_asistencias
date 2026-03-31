package com.universidad.control_asistencia.service.interfaces;

import com.universidad.control_asistencia.model.LoginConfig;

public interface LoginConfigInterface {
    boolean isLoginHabilitado(String rol);
    LoginConfig cambiarEstadoLogin(String rol);
    LoginConfig obtenerEstado(String rol);
}