package com.universidad.control_asistencia.service;

import com.universidad.control_asistencia.service.interfaces.AsistenciaAlumnoInterface;
import com.universidad.control_asistencia.model.dto.AsistenciaAlumnoDTO;
import org.springframework.stereotype.Service;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@Service
public class AsistenciaAlumnoService implements AsistenciaAlumnoInterface {

    private final String MODEL_PATH = "/controll_asistencia.model";
    private final String ARFF_STRUCTURE = "/control_asistencia_mongo.asistencias.arff";

    /**
     * ✅ Realiza la predicción usando Weka
     */
    @Override
    public String predecir(AsistenciaAlumnoDTO dto) throws Exception {

        // 1️⃣ Cargar estructura del ARFF
        InputStream arffInput = getClass().getResourceAsStream(ARFF_STRUCTURE);
        if (arffInput == null) {
            throw new IllegalStateException("No se encontró el archivo ARFF: " + ARFF_STRUCTURE);
        }

        Instances structure = new Instances(new InputStreamReader(arffInput));
        structure.setClassIndex(structure.numAttributes() - 1); // "estado"

        // 2️⃣ Crear una instancia basada en la estructura
        Instance instancia = (Instance) structure.firstInstance().copy();
        instancia.setDataset(structure);

        // 3️⃣ Asignar valores según el orden del ARFF
        instancia.setValue(0, dto.getNombre());
        instancia.setValue(1, dto.getClase());
        instancia.setValue(2, dto.getDiaSemana());
        instancia.setValue(3, dto.getHora());
        instancia.setValue(4, dto.getMes());

        // 4️⃣ Cargar el modelo entrenado
        InputStream modelStream = getClass().getResourceAsStream(MODEL_PATH);
        if (modelStream == null) {
            throw new IllegalStateException("No se encontró el archivo MODEL: " + MODEL_PATH);
        }

        Classifier classifier = (Classifier) SerializationHelper.read(modelStream);

        // 5️⃣ Clasificar la instancia
        double predIndex = classifier.classifyInstance(instancia);
        return structure.classAttribute().value((int) predIndex);
    }

    /**
     * ✅ Cargar dinámicamente las listas de valores nominales
     * (para los <select> del formulario HTML)
     */
    @Override
    public Map<String, List<String>> cargarOpcionesDesdeArff() {
        try {
            InputStream arffInput = getClass().getResourceAsStream(ARFF_STRUCTURE);
            if (arffInput == null) {
                throw new IllegalStateException("No se encontró el archivo ARFF: " + ARFF_STRUCTURE);
            }

            Instances structure = new Instances(new InputStreamReader(arffInput));
            structure.setClassIndex(structure.numAttributes() - 1);

            Map<String, List<String>> mapa = new LinkedHashMap<>();

            for (int i = 0; i < structure.numAttributes(); i++) {
                Attribute attr = structure.attribute(i);

                if (attr.isNominal()) {
                    List<String> valores = new ArrayList<>();
                    for (int j = 0; j < attr.numValues(); j++) {
                        valores.add(attr.value(j));
                    }
                    mapa.put(attr.name(), valores);
                }
            }

            return mapa;

        } catch (Exception e) {
            throw new RuntimeException("Error leyendo valores del ARFF: " + e.getMessage(), e);
        }
    }
}
