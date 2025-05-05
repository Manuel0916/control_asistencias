package com.universidad.control_asistencia.service;

import com.universidad.control_asistencia.model.Asistencia;
import com.universidad.control_asistencia.repository.AsistenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AsistenciaService {

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    // Registrar una nueva asistencia
    public Asistencia registrarAsistencia(Asistencia asistencia) {
        // Aquí puedes agregar alguna lógica adicional si es necesario, por ejemplo:
        // Validar que el estudiante no haya registrado ya su asistencia en la misma clase en la misma fecha.
        return asistenciaRepository.save(asistencia);
    }

    // Obtener todas las asistencias
    public List<Asistencia> obtenerTodas() {
        return asistenciaRepository.findAll();
    }

    // Método para encontrar asistencias por clase
    public List<Asistencia> obtenerPorClase(String clase) {
        return asistenciaRepository.findByClase(clase);
    }

    // Método para encontrar asistencias por nombre
    public List<Asistencia> obtenerPorNombre(String nombre) {
        return asistenciaRepository.findByNombre(nombre);
    }

    // Método para encontrar asistencias dentro de un rango de fechas
    public List<Asistencia> obtenerPorRangoDeFechas(LocalDateTime startDate, LocalDateTime endDate) {
        return asistenciaRepository.findByFechaHoraBetween(startDate, endDate);
    }
}
