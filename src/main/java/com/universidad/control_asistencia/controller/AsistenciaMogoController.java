package com.universidad.control_asistencia.controller;

import com.universidad.control_asistencia.model.dto.Asistenciamongo;
import com.universidad.control_asistencia.repository.AsistenciaMongoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// CAMBIO AQUÍ: Ahora la ruta es diferente a la de MySQL
@RequestMapping("/api/mongo/asistencias")
public class AsistenciaMogoController {

    private final AsistenciaMongoRepository asistenciaMongoRepository;

    public AsistenciaMogoController(AsistenciaMongoRepository asistenciaMongoRepository) {
        this.asistenciaMongoRepository = asistenciaMongoRepository;
    }

    @GetMapping
    public List<Asistenciamongo> obtenerAsistencias() {
        return asistenciaMongoRepository.findAll();
    }
}