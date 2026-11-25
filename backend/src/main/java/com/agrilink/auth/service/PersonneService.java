package com.agrilink.auth.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.agrilink.auth.entity.Personne;
import com.agrilink.auth.repository.PersonneRepository;

@Service
public class PersonneService {

    private final PersonneRepository personneRepository;

    public PersonneService(PersonneRepository personneRepository) {
        this.personneRepository = personneRepository;
    }

    // Rechercher une personne par son email
    public Optional<Personne> rechercherParEmail(String email) {
        return personneRepository.findByEmail(email);
    }

    // Vérifier si un email existe déjà
    public boolean emailExiste(String email) {
        return personneRepository.existsByEmail(email);
    }

    // Rechercher une personne par son identifiant
    public Optional<Personne> rechercherParId(Long id) {
        return personneRepository.findById(id);
    }
}
