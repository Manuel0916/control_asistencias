package com.universidad.control_asistencia.Repository;

import com.universidad.control_asistencia.Model.Profesor_Clase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProfesorClaseRepository extends JpaRepository<Profesor_Clase, Long> {
    List<Profesor_Clase> findByProfesor_ProfesorId(Long profesorId);
    List<Profesor_Clase> findByClase_ClaseId(Long claseId);
}