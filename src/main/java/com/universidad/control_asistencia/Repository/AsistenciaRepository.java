package com.universidad.control_asistencia.Repository;

import com.universidad.control_asistencia.Model.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {
    List<Asistencia> findByEstudiante_EstudianteId(Long estudianteId);
    List<Asistencia> findByHorario_HorarioId(Long horarioId);
    List<Asistencia> findByFecha(LocalDate fecha);
    List<Asistencia> findByEstudiante_EstudianteIdAndFecha(Long estudianteId, LocalDate fecha);
    List<Asistencia> findByHorario_HorarioIdAndFecha(Long horarioId, LocalDate fecha);
}