package com.universidad.control_asistencia.repository;

import com.universidad.control_asistencia.model.HorarioClase;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface HorarioClaseRepository extends MongoRepository<HorarioClase, String> {
    List<HorarioClase> findByClase(String clase);
}
