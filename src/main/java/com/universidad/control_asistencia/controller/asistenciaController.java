package com.universidad.control_asistencia.controller;

import com.universidad.control_asistencia.model.Asistencia;
import com.universidad.control_asistencia.service.AsistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/asistencias")
@CrossOrigin(origins = "*")
public class asistenciaController {

    @Autowired
    private AsistenciaService asistenciaService;

    // Registrar asistencia
    @PostMapping
    public Asistencia registrarAsistencia(@RequestBody Asistencia asistencia) {
        // Asegúrate de que la fecha y hora se están manejando correctamente
        if (asistencia.getFechaHora() == null) {
            asistencia.setFechaHora(LocalDateTime.now());  // Si la fecha no se pasa, se asigna la actual
        }
        // Puedes agregar más validaciones si es necesario, por ejemplo, para verificar si el nombre o la clase son válidos
        return asistenciaService.registrarAsistencia(asistencia);
    }

    // Listar todas las asistencias
    @GetMapping
    public List<Asistencia> listarAsistencias() {
        return asistenciaService.obtenerTodas();
    }

    // Buscar asistencias por clase
    @GetMapping("/clase/{clase}")
    public List<Asistencia> obtenerAsistenciasPorClase(@PathVariable String clase) {
        return asistenciaService.obtenerPorClase(clase);
    }

    // Buscar asistencias por nombre
    @GetMapping("/nombre/{nombre}")
    public List<Asistencia> obtenerAsistenciasPorNombre(@PathVariable String nombre) {
        return asistenciaService.obtenerPorNombre(nombre);
    }

    // Buscar asistencias por rango de fechas
    @GetMapping("/rango")
    public List<Asistencia> obtenerAsistenciasPorRangoDeFechas(@RequestParam String startDate, @RequestParam String endDate) {
        // Convertir las fechas desde el formato recibido por la URL
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        return asistenciaService.obtenerPorRangoDeFechas(start, end);
    }
}
