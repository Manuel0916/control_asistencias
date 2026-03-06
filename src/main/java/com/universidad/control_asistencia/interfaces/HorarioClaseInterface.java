package com.universidad.control_asistencia.interfaces;

import com.universidad.control_asistencia.model.HorarioClase;

import java.util.List;

public interface HorarioClaseInterface {

    List<HorarioClase> obtenerHorariosPorClase(String clase);
    HorarioClase guardarHorario(HorarioClase horario);
    List<HorarioClase> obtenerTodos();
    boolean esHorarioActivo(String clase);


}
