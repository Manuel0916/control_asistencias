

package com.universidad.control_asistencia.model.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "asistencias")
public class Asistenciamongo {

    @Id
    private String id;

    private String nombre;
    private String clase;
    private Date fechahora;
    private String dia_semana;
    private String hora;
    private Double hora_num;
    private String mes;
    private String estado;
}