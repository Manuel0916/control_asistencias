package com.universidad.control_asistencia.controller;

import com.universidad.control_asistencia.service.interfaces.HorarioClaseInterface;
import com.universidad.control_asistencia.model.HorarioClase;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/horarios")
public class HorarioClaseController {

    private HorarioClaseInterface horarioClaseInterface;

    @GetMapping
    public List<HorarioClase> obtenerTodos() {
        return horarioClaseInterface.obtenerTodos();
    }

    @GetMapping("/{clase}")
    public List<HorarioClase> obtenerPorClase(@PathVariable String clase) {
        return horarioClaseInterface.obtenerHorariosPorClase(clase);
    }

    @PostMapping
    public HorarioClase guardar(@RequestBody HorarioClase horario) {
        return horarioClaseInterface.guardarHorario(horario);
    }

    @GetMapping("/activo/{clase}")
    public boolean horarioActivo(@PathVariable String clase) {
        return horarioClaseInterface.esHorarioActivo(clase);
    }
}
