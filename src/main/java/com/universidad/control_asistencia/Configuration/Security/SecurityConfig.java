package com.universidad.control_asistencia.Configuration.Security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${security.rememberme.key}")
    private String rememberMeKey;

    @Value("${security.rememberme.token-validity}")
    private int rememberMeValidity;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .rememberMe(remember -> remember
                    .key(rememberMeKey)
                    .tokenValiditySeconds(rememberMeValidity)
                    .rememberMeParameter("remember-me")
                    .useSecureCookie(true)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/forgot-password", "/css/**", "/js/**", "/img/**").permitAll()
                        .requestMatchers("/asistencia/api/predict").permitAll()
                        .requestMatchers("/registro").hasRole("COORDINADOR")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/coordinador/**").hasRole("COORDINADOR")
                        .requestMatchers("/estudiante/**").hasRole("ESTUDIANTE")
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler(customAuthenticationSuccessHandler())
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID", "access_token", "remember-me")
                        .clearAuthentication(true)
                        .permitAll()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).invalidSessionUrl("/login?error=true")
                        .maximumSessions(1)
                        .sessionRegistry(sessionRegistry())
                        .expiredUrl("/login?expired=true")
                );
        return http.build();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                Authentication authentication) throws IOException, ServletException {
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
                    response.sendRedirect("/login?error");
                }
            }
        };
    }

}