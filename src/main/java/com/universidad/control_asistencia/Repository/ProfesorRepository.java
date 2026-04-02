package com.universidad.control_asistencia.Repository;

import com.universidad.control_asistencia.Model.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, Long> {
    Optional<Profesor> findByUsuario_UsuarioId(Long usuarioId);
}
