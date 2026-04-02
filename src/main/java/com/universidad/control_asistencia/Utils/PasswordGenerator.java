package com.universidad.control_asistencia.Utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String nuevaContraCoordinador = encoder.encode("123456");
        String nuevaContraEstudiante = encoder.encode("123456");
        String nuevaContraDocente = encoder.encode("123456");

        System.out.println("Coordinador: " + nuevaContraCoordinador);
        System.out.println("Estudiante: " + nuevaContraEstudiante);
        System.out.println("Docente: " + nuevaContraDocente);
    }

}