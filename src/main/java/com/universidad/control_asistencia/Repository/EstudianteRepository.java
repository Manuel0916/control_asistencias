package com.universidad.control_asistencia.Repository;

import com.universidad.control_asistencia.Model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    Optional<Estudiante> findByUsuario_UsuarioId(Long usuarioId);
    Optional<Estudiante> findByCodigoEstudiante(String codigoEstudiante);
}