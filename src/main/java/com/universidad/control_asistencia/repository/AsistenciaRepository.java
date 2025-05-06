package com.universidad.control_asistencia.repository;

import com.universidad.control_asistencia.model.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {

    // Consulta para encontrar asistencias por clase
    List<Asistencia> findByClase(String clase);

    // Consulta para encontrar asistencias por nombre de estudiante
    List<Asistencia> findByNombre(String nombre);

    // Consulta para encontrar asistencias por fecha
    List<Asistencia> findByFechaHoraBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Consulta para encontrar asistencias por clase y estado (puede ser útil para filtrar asistencias)
    List<Asistencia> findByClaseAndEstado(String clase, String estado);
}
