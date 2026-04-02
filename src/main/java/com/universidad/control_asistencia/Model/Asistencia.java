package com.universidad.control_asistencia.Model;

import com.universidad.control_asistencia.Model.Enum.Estado;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long asistenciaId;

    @ManyToOne
    @JoinColumn(name = "estudiante_id")
    private Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name = "horario_id")
    private Horario horario;

    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    private Estado estado;

}