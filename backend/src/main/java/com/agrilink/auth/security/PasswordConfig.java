package com.agrilink.auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
// ce classe contient une configuration 
public class PasswordConfig {

    @Bean
    // demande à Spring de créer et gérer cet objet
    // BCrypt est un encodage avec hachage
     PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

/*Spring  crée automatiquement un objet PasswordEncoder utilisant BCrypt pour sécuriser les mots de passe*/