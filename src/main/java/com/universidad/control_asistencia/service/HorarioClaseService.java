package com.universidad.control_asistencia.service;

import com.universidad.control_asistencia.interfaces.HorarioClaseInterface;
import com.universidad.control_asistencia.model.HorarioClase;
import com.universidad.control_asistencia.repository.HorarioClaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Service
public class HorarioClaseService implements HorarioClaseInterface {

    @Autowired
    private HorarioClaseRepository repository;

    @Override
    public List<HorarioClase> obtenerHorariosPorClase(String clase) {
        return repository.findByClase(clase);
    }

    @Override
    public HorarioClase guardarHorario(HorarioClase horario) {
        return repository.save(horario);
    }

    @Override
    public List<HorarioClase> obtenerTodos() {
        return repository.findAll();
    }

    @Override
    public boolean esHorarioActivo(String clase) {
        List<HorarioClase> horarios = repository.findByClase(clase);

        LocalTime ahora = LocalTime.now();
        DayOfWeek hoy = LocalDate.now().getDayOfWeek();

        for (HorarioClase h : horarios) {
            if (h.getDias().stream().anyMatch(d -> diaCoincide(d, hoy))) {
                LocalTime inicio = LocalTime.parse(h.getHoraInicio(), DateTimeFormatter.ofPattern("HH:mm"));
                LocalTime fin = LocalTime.parse(h.getHoraFin(), DateTimeFormatter.ofPattern("HH:mm"));
                if (!ahora.isBefore(inicio) && !ahora.isAfter(fin)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean diaCoincide(String diaGuardado, DayOfWeek hoy) {
        String diaActual = hoy.getDisplayName(java.time.format.TextStyle.FULL, new Locale("es")).toLowerCase();
        return diaGuardado.toLowerCase().equals(diaActual);
    }
}
