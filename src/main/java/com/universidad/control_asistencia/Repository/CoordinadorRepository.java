package com.universidad.control_asistencia.Repository;

import com.universidad.control_asistencia.Model.Coordinador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CoordinadorRepository extends JpaRepository<Coordinador, Long> {
    Optional<Coordinador> findByUsuario_UsuarioId(Long usuarioId);
}