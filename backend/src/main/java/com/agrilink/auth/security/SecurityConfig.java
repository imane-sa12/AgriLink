package com.agrilink.auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
// Active la configuration de sécurité web
// SecurityFilterChain : definit les regles de sécurité des requêtes HTTP
// csrf.disable() :Désactive la protection CSRF

public class SecurityConfig {

    @Bean
     SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
            // Désactiver CSRF pour notre API REST pendant le développement
            .csrf(csrf -> csrf.disable())

            // Autoriser temporairement les requêtes API
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            )

            // Désactiver la page de connexion automatique
            .formLogin(form -> form.disable())

            // Désactiver l'authentification HTTP Basic
            .httpBasic(basic -> basic.disable());

        return http.build();
    }
}
