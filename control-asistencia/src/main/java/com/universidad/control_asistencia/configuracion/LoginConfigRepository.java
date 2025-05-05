package com.universidad.control_asistencia.configuracion;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginConfigRepository extends JpaRepository<LoginConfig, Long> {
    Optional<LoginConfig> findByRol(String rol);
}
