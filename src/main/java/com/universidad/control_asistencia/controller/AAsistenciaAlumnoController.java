package com.universidad.control_asistencia.controller;

import com.universidad.control_asistencia.interfaces.AsistenciaAlumnoInterface;
import com.universidad.control_asistencia.model.dto.AsistenciaAlumnoDTO;
import com.universidad.control_asistencia.service.AsistenciaAlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/asistencia")
public class AAsistenciaAlumnoController {

    private AsistenciaAlumnoInterface asistenciaAlumnoInterface;

    // ================== ✅ FORMULARIO HTML ==================
    @GetMapping("/form")
    public String mostrarFormulario(Model model) {

        // Obtener las opciones dinámicas del ARFF
        Map<String, java.util.List<String>> opciones = asistenciaAlumnoInterface.cargarOpcionesDesdeArff();

        model.addAttribute("asistenciaDto", new AsistenciaAlumnoDTO());
        model.addAttribute("nombres", opciones.get("nombre"));
        model.addAttribute("clases", opciones.get("clase"));
        model.addAttribute("dias", opciones.get("dia_semana"));
        model.addAttribute("horas", opciones.get("hora"));
        model.addAttribute("meses", opciones.get("mes"));

        return "formulario_alumno"; // archivo HTML (templates/formulario_alumno.html)
    }

    // ================== ✅ RESULTADO HTML ==================
    @PostMapping("/predict")
    public String predecirHtml(@ModelAttribute AsistenciaAlumnoDTO dto, Model model) {
        try {
            String estado = asistenciaAlumnoInterface.predecir(dto);
            dto.setEstado(estado);

            model.addAttribute("resultado", estado);
            model.addAttribute("asistencia", dto);

            return "resultado_alumno";

        } catch (Exception e) {
            model.addAttribute("error", "Error al predecir: " + e.getMessage());
            return "resultado_alumno";
        }
    }

    // ================== ✅ API JSON (Postman) ==================
    @PostMapping("/api/predict")
    @ResponseBody
    public ResponseEntity<?> predecirApi(@RequestBody AsistenciaAlumnoDTO dto) {
        try {
            String estado = asistenciaAlumnoInterface.predecir(dto);
            dto.setEstado(estado);
            return ResponseEntity.ok(dto);

        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "ok", false,
                            "error", e.getMessage()
                    ));
        }
    }
}
