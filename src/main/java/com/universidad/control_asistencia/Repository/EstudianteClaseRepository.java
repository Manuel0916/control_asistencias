package com.universidad.control_asistencia.Repository;

import com.universidad.control_asistencia.Model.Estudiante_Clase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EstudianteClaseRepository extends JpaRepository<Estudiante_Clase, Long> {
    List<Estudiante_Clase> findByEstudiante_EstudianteId(Long estudianteId);
    List<Estudiante_Clase> findByClase_ClaseId(Long claseId);
}