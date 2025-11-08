package org.example.cuidadodemascotas.servicemicroservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)  // Habilita @PreAuthorize
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(auth -> auth
                        // ========== SWAGGER Y OPENAPI ==========
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()

                        // ========== ACTUATOR ==========
                        .requestMatchers("/actuator/**").permitAll()

                        // ========== ENDPOINTS PÚBLICOS (CONSULTA) ==========
                        .requestMatchers(
                                "/service-types",
                                "/service-types/{id}",
                                "/services",
                                "/services/{id}",
                                "/services/search"
                        ).permitAll()

                        // ========== ENDPOINTS PROTEGIDOS ==========
                        // POST, PUT, DELETE requieren autenticación (manejado por @PreAuthorize)
                        .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }
}