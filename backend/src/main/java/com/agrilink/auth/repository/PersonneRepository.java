package com.agrilink.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agrilink.auth.entity.Personne;

// permet de communiquer avec la base de donnee 
public interface PersonneRepository extends JpaRepository<Personne, Long> {
	
	// Permet de rechercher une personne à partir de son email
    Optional<Personne> findByEmail(String email);

    // Permet de vérifier si un email existe déjà dans la base de données
    boolean existsByEmail(String email);

}
