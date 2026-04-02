package com.universidad.control_asistencia.Repository;

import com.universidad.control_asistencia.Model.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long> {
    List<Horario> findByProfesor_ProfesorId(Long profesorId);
    List<Horario> findByClase_ClaseId(Long claseId);
    List<Horario> findByDiaSemana(String diaSemana);
}