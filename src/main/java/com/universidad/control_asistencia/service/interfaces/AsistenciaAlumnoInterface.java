package com.universidad.control_asistencia.service.interfaces;

import com.universidad.control_asistencia.model.dto.AsistenciaAlumnoDTO;

import java.util.List;
import java.util.Map;

public interface AsistenciaAlumnoInterface {
    Map<String, List<String>> cargarOpcionesDesdeArff();
    String predecir(AsistenciaAlumnoDTO dto) throws Exception;
}