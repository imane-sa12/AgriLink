package com.agrilink.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.agrilink.auth.entity.Administrateur;
import com.agrilink.auth.enums.StatutCompte;
import com.agrilink.auth.repository.PersonneRepository;

@Configuration
public class AdminInitializer {

    @Value("${app.admin.email}")
    private String email;

    @Value("${app.admin.password}")
    private String motDePasse;

    @Value("${app.admin.nom}")
    private String nom;

    @Value("${app.admin.prenom}")
    private String prenom;

    @Value("${app.admin.telephone}")
    private String telephone;

    @Bean
    CommandLineRunner initialiserAdministrateur(
            PersonneRepository personneRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            // Vérifier si l'administrateur existe déjà.
            if (personneRepository.existsByEmail(email.trim().toLowerCase())) {
                System.out.println("Le compte administrateur existe déjà.");
                return;
            }

            // Créer le compte administrateur.
            Administrateur admin = new Administrateur();

            admin.setNom(nom);
            admin.setPrenom(prenom);
            admin.setEmail(email.trim().toLowerCase());
            admin.setTelephone(telephone);

            // Chiffrer le mot de passe.
            admin.setMotDePasse(passwordEncoder.encode(motDePasse));

            // Activer le compte administrateur.
            admin.setStatutCompte(StatutCompte.ACTIF);

            // Enregistrer l'administrateur.
            personneRepository.save(admin);

            System.out.println("Compte administrateur créé avec succès.");
        };
    }
}
