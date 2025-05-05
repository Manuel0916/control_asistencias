package com.universidad.control_asistencia.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long usuarioId; // Nuevo: referencia al estudiante
    private String nombre;
    private String clase;
    private String estado;

    private LocalDateTime fechaHora; // Cambiar de LocalDate a LocalDateTime

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }




    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    // Método para obtener la fecha en formato String (si es necesario para el front-end)
    public String getFechaHoraString() {
        return fechaHora != null ? fechaHora.toString() : "No definida";
    }
}
