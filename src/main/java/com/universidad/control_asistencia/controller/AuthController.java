package com.universidad.control_asistencia.controller;

import com.universidad.control_asistencia.model.Rol;
import com.universidad.control_asistencia.model.Usuario;
import com.universidad.control_asistencia.repository.RolRepository;
import com.universidad.control_asistencia.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Controller
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UsuarioRepository usuarioRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            @RequestParam(value = "registroExitoso", required = false) String registroExitoso,
                            Model model) {
        if (error != null) {
            model.addAttribute("error", "Usuario o contraseña incorrectos.");
        }
        if (logout != null) {
            model.addAttribute("logout", "Has cerrado sesión correctamente.");
        }
        if (registroExitoso != null) {
            model.addAttribute("success", "Registro exitoso. Ahora puedes iniciar sesión.");
        }
        return "login";
    }

    @GetMapping("/registro")
    public String registroPage(Model model) {
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(@RequestParam String username,
                                   @RequestParam String email,
                                   @RequestParam String password,
                                   @RequestParam String rolNombre,
                                   Model model) {

        System.out.println("Intentando registrar usuario con los siguientes datos:");
        System.out.println("Username: " + username);
        System.out.println("Email: " + email);
        System.out.println("Rol: " + rolNombre);

        if (usuarioRepository.existsByUsername(username)) {
            model.addAttribute("error", "El nombre de usuario ya está en uso.");
            return "registro";
        }

        if (usuarioRepository.existsByEmail(email)) {
            model.addAttribute("error", "El correo ya está en uso.");
            return "registro";
        }

        Optional<Rol> rolExistente = rolRepository.findByNombre("ROLE_" + rolNombre.toUpperCase());
        if (rolExistente.isEmpty()) {
            model.addAttribute("error", "El rol seleccionado no es válido.");
            return "registro";
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsername(username);
        nuevoUsuario.setEmail(email);
        nuevoUsuario.setPassword(passwordEncoder.encode(password));

        Set<Rol> roles = new HashSet<>();
        roles.add(rolExistente.get());
        nuevoUsuario.setRoles(roles);

        try {
            usuarioRepository.save(nuevoUsuario);
            System.out.println("Usuario registrado exitosamente: " + username);
            return "redirect:/login?registroExitoso=true";
        } catch (Exception e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
            model.addAttribute("error", "Hubo un error al registrar el usuario. Inténtalo de nuevo.");
            return "registro";
        }
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String recuperarPassword(@RequestParam String email, Model model) {
        if (!usuarioRepository.existsByEmail(email)) {
            model.addAttribute("error", "No se encontró un usuario con ese correo.");
            return "forgot-password";
        }

        model.addAttribute("success", "Si el correo es válido, recibirás instrucciones para restablecer tu contraseña.");
        return "forgot-password";
    }

    // ✅ Endpoint modificado para devolver el nombre real del usuario
    @GetMapping("/api/usuario-actual")
    @ResponseBody
    public ResponseEntity<String> obtenerUsuarioActual(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("No autenticado");
        }

        String email = authentication.getName(); // Spring Security usa el email como principal
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

        if (usuarioOpt.isPresent()) {
            return ResponseEntity.ok(usuarioOpt.get().getUsername());  // Devolver el nombre real
        } else {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }
    }
}
