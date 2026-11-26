
package com.agrilink.auth.service;

import org.springframework.http.HttpStatus;
import com.agrilink.auth.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.agrilink.auth.dto.InscriptionAgriculteurRequest;
import com.agrilink.auth.entity.Agriculteur;
import com.agrilink.auth.enums.StatutCompte;
import com.agrilink.auth.repository.AgriculteurRepository;
import com.agrilink.auth.repository.PersonneRepository;

import com.agrilink.auth.dto.ConnexionRequest;
import com.agrilink.auth.dto.ConnexionResponse;
import com.agrilink.auth.entity.ExpertAgricole;
import com.agrilink.auth.entity.Administrateur;
import com.agrilink.auth.entity.Personne;

@Service
// ce classe contient la logique metier 
public class AuthService {

	private final AgriculteurRepository agriculteurRepository;
	private final PersonneRepository personneRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;

	public AuthService(AgriculteurRepository agriculteurRepository, PersonneRepository personneRepository,
			PasswordEncoder passwordEncoder , JwtService jwtService) {

		this.agriculteurRepository = agriculteurRepository;
		this.personneRepository = personneRepository;
		this.passwordEncoder = passwordEncoder;
	    this.jwtService = jwtService;

	}

	@Transactional
	// l'opération d'inscription est exécutée dans une transaction
	public void inscrireAgriculteur(InscriptionAgriculteurRequest request) {

		// 1. Vérifier si l'email existe déjà
		String email = request.getEmail().trim().toLowerCase();

		if (personneRepository.existsByEmail(email)) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Cette adresse email est déjà utilisée.");
		}

		// 2. Créer un nouvel agriculteur
		Agriculteur agriculteur = new Agriculteur();

		// 3. Enregistrer les informations personnelles
		agriculteur.setNom(request.getNom());
		agriculteur.setPrenom(request.getPrenom());
		agriculteur.setEmail(email);
		agriculteur.setTelephone(request.getTelephone());

		// 4. Chiffrer le mot de passe
		agriculteur.setMotDePasse(passwordEncoder.encode(request.getMotDePasse()));

		// 5. Définir le statut initial du compte
		agriculteur.setStatutCompte(StatutCompte.ACTIF);

		// 6. Enregistrer les informations de l'exploitation
		agriculteur.setNomExploitation(request.getNomExploitation());
		agriculteur.setSuperficie(request.getSuperficie());
		agriculteur.setModeLocalisation(request.getModeLocalisation());
		agriculteur.setRegion(request.getRegion());
		agriculteur.setProvince(request.getProvince());
		agriculteur.setCommune(request.getCommune());
		agriculteur.setDouar(request.getDouar());
		agriculteur.setLatitude(request.getLatitude());
		agriculteur.setLongitude(request.getLongitude());
		agriculteur.setPrecisionLocalisation(request.getPrecisionLocalisation());

		// 7. Enregistrer l'agriculteur dans la base de données
		agriculteurRepository.save(agriculteur);
	}

	public ConnexionResponse connecter(ConnexionRequest request) {

		// 1. Rechercher la personne par email
		String email = request.getEmail().trim().toLowerCase();

		Personne personne = personneRepository.findByEmail(email).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email ou mot de passe incorrect"));

		// 2. Vérifier le mot de passe
		boolean motDePasseCorrect = passwordEncoder.matches(request.getMotDePasse(), personne.getMotDePasse());

		if (!motDePasseCorrect) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email ou mot de passe incorrect");
		}

		// 3. Vérifier le statut du compte
		if (personne.getStatutCompte() != StatutCompte.ACTIF) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Votre compte n'est pas actif");
		}

		// 4. Identifier le rôle
		String role;

		if (personne instanceof Agriculteur) {
			role = "AGRICULTEUR";
		} else if (personne instanceof ExpertAgricole) {
			role = "EXPERT";
		} else if (personne instanceof Administrateur) {
			role = "ADMIN";
		} else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Type de compte non reconnu");
		}

		// 5. Générer le token JWT
		String token = jwtService.genererToken(
		        personne.getIdUtilisateur(),
		        personne.getEmail(),
		        role
		);

		// 6. Retourner les informations et le token
		return new ConnexionResponse(
		        personne.getIdUtilisateur(),
		        personne.getNom(),
		        personne.getPrenom(),
		        personne.getEmail(),
		        role,
		        token
		);
	}

}
/*
 * vérifier si l'adresse e-mail existe déjà chiffrer le mot de passe avec BCrypt
 * enregistrer les informations de l'agriculteur dans MySQL attribuer
 * automatiquement le statut ACTIF au compte car la validation administrativwe
 * concerne les comptes des experts
 **/