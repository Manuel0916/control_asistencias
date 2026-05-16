package com.universidad.control_asistencia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http

                // DESACTIVAR CSRF PARA APIs
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(
                                "/ia/chat",
                                "/ia/**",
                                "/api/**",
                                "/api/mongo/**"
                        )
                )

                // AUTORIZACIONES
                .authorizeHttpRequests(auth -> auth

                        // RUTAS PUBLICAS
                        .requestMatchers(
                                "/",
                                "/login",
                                "/forgot-password",
                                "/css/**",
                                "/js/**",
                                "/img/**"
                        ).permitAll()

                        // IA
                        .requestMatchers(
                                "/ia/chat",
                                "/ia/**"
                        ).permitAll()

                        // API PREDICCION
                        .requestMatchers(
                                "/asistencia/api/predict"
                        ).permitAll()

                        // API MONGO POWER BI
                        .requestMatchers(
                                "/api/**",
                                "/api/mongo/**"
                        ).permitAll()

                        // ROLES
                        .requestMatchers("/registro").hasRole("COORDINADOR")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/coordinador/**").hasRole("COORDINADOR")
                        .requestMatchers("/estudiante/**").hasRole("ESTUDIANTE")

                        // CUALQUIER OTRA RUTA
                        .anyRequest().authenticated()
                )

                // LOGIN
                .formLogin(login -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler(customAuthenticationSuccessHandler())
                        .failureUrl("/login?error=true")
                        .permitAll()
                )

                // LOGOUT
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )

                // SESIONES
                .sessionManagement(session -> session
                        .maximumSessions(1)
                        .expiredUrl("/login?expired=true")
                );

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {

        return new AuthenticationSuccessHandler() {

            @Override
            public void onAuthenticationSuccess(
                    HttpServletRequest request,
                    HttpServletResponse response,
                    Authentication authentication
            ) throws IOException, ServletException {

                if (authentication.getAuthorities().stream()
                        .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {

                    response.sendRedirect("/admin/dashboard");

                } else if (authentication.getAuthorities().stream()
                        .anyMatch(auth -> auth.getAuthority().equals("ROLE_COORDINADOR"))) {

                    response.sendRedirect("/coordinador/dashboard");

                } else if (authentication.getAuthorities().stream()
                        .anyMatch(auth -> auth.getAuthority().equals("ROLE_ESTUDIANTE"))) {

                    response.sendRedirect("/estudiante/dashboard");

                } else {

                    response.sendRedirect("/login?error=sin-rol");
                }
            }
        };
    }
}