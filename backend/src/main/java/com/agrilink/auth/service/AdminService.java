
package com.agrilink.auth.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.agrilink.auth.dto.ExpertEnAttenteDTO;

import com.agrilink.auth.entity.Administrateur;
import com.agrilink.auth.entity.ExpertAgricole;
import com.agrilink.auth.entity.Personne;

import com.agrilink.auth.enums.StatutCompte;
import com.agrilink.auth.enums.StatutValidation;

import com.agrilink.auth.repository.ExpertAgricoleRepository;
import com.agrilink.auth.repository.PersonneRepository;

@Service
public class AdminService {

	private final ExpertAgricoleRepository expertRepository;
	private final PersonneRepository personneRepository;

	public AdminService(ExpertAgricoleRepository expertRepository, PersonneRepository personneRepository) {

		this.expertRepository = expertRepository;
		this.personneRepository = personneRepository;
	}

	// Consulter les experts en attente
	public List<ExpertEnAttenteDTO> consulterExpertsEnAttente() {

		return expertRepository.findByStatutValidation(StatutValidation.EN_ATTENTE).stream()
				.map(ExpertEnAttenteDTO::new).toList();
	}

	// Valider un compte expert
	@Transactional
	public void validerExpert(Long id) {

		ExpertAgricole expert = trouverExpert(id);

		if (expert.getStatutValidation() != StatutValidation.EN_ATTENTE) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Ce compte expert n'est pas en attente de validation");
		}

		expert.setStatutValidation(StatutValidation.VALIDE);
		expert.setStatutCompte(StatutCompte.ACTIF);

		expertRepository.save(expert);
	}

	// Refuser un compte expert
	@Transactional
	public void refuserExpert(Long id) {

		ExpertAgricole expert = trouverExpert(id);

		if (expert.getStatutValidation() != StatutValidation.EN_ATTENTE) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Ce compte expert n'est pas en attente de validation");
		}

		expert.setStatutValidation(StatutValidation.REFUSE);
		expert.setStatutCompte(StatutCompte.INACTIF);

		expertRepository.save(expert);
	}

	// Rechercher un expert par son identifiant
	private ExpertAgricole trouverExpert(Long id) {

		return expertRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Expert introuvable"));
	}

	// Désactiver un compte agriculteur ou expert validé
	@Transactional
	public void desactiverCompte(Long idCompte, String emailAdminCourant) {

		// Vérifier l'administrateur connecté
		Personne admin = personneRepository.findByEmail(emailAdminCourant)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Administrateur introuvable"));

		if (!(admin instanceof Administrateur)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Accès réservé à l'administrateur");
		}

		// Empêcher l'administrateur de désactiver son propre compte
		if (admin.getIdUtilisateur().equals(idCompte)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Vous ne pouvez pas désactiver votre propre compte");
		}

		// Rechercher le compte à désactiver
		Personne compte = personneRepository.findById(idCompte)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Compte introuvable"));

		// Vérifier que le compte est actif
		if (compte.getStatutCompte() != StatutCompte.ACTIF) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Ce compte n'est pas actif");
		}

		// Vérifier que l'expert a été validé
		if (compte instanceof ExpertAgricole expert) {

			if (expert.getStatutValidation() != StatutValidation.VALIDE) {
				throw new ResponseStatusException(HttpStatus.CONFLICT, "Seul un expert validé peut être désactivé");
			}
		}

		// Désactiver le compte
		compte.setStatutCompte(StatutCompte.INACTIF);

		personneRepository.save(compte);
	}

	// Réactiver un compte précédemment désactivé
	@Transactional
	public void reactiverCompte(Long idCompte) {

		// 1. Rechercher le compte
		Personne compte = personneRepository.findById(idCompte)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Compte introuvable"));

		// 2. Vérifier que le compte est inactif
		if (compte.getStatutCompte() != StatutCompte.INACTIF) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Ce compte n'est pas désactivé");
		}

		// 3. Vérifier le statut de validation si c'est un expert
		if (compte instanceof ExpertAgricole expert) {

			if (expert.getStatutValidation() != StatutValidation.VALIDE) {
				throw new ResponseStatusException(HttpStatus.CONFLICT,
						"Impossible de réactiver un expert non validé ou refusé");
			}
		}

		// 4. Réactiver le compte
		compte.setStatutCompte(StatutCompte.ACTIF);

		personneRepository.save(compte);
	}

	// Suspendre un compte actif
	@Transactional
	public void suspendreCompte(Long idCompte, String emailAdminCourant) {

		// 1. Vérifier l'administrateur connecté
		Personne admin = personneRepository.findByEmail(emailAdminCourant)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Administrateur introuvable"));

		if (!(admin instanceof Administrateur)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Accès réservé à l'administrateur");
		}

		// 2. Empêcher l'administrateur de suspendre son propre compte
		if (admin.getIdUtilisateur().equals(idCompte)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Vous ne pouvez pas suspendre votre propre compte");
		}

		// 3. Rechercher le compte
		Personne compte = personneRepository.findById(idCompte)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Compte introuvable"));

		// 4. Vérifier que le compte est actif
		if (compte.getStatutCompte() != StatutCompte.ACTIF) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Seul un compte actif peut être suspendu");
		}

		// 5. Vérifier que l'expert est validé
		if (compte instanceof ExpertAgricole expert) {
			if (expert.getStatutValidation() != StatutValidation.VALIDE) {
				throw new ResponseStatusException(HttpStatus.CONFLICT, "Seul un expert validé peut être suspendu");
			}
		}

		// 6. Suspendre le compte
		compte.setStatutCompte(StatutCompte.SUSPENDU);
		personneRepository.save(compte);
	}

	// Lever la suspension d'un compte
	@Transactional
	public void leverSuspension(Long idCompte) {

		// 1. Rechercher le compte
		Personne compte = personneRepository.findById(idCompte)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Compte introuvable"));

		// 2. Vérifier que le compte est suspendu
		if (compte.getStatutCompte() != StatutCompte.SUSPENDU) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Ce compte n'est pas suspendu");
		}

		// 3. Vérifier que l'expert est validé
		if (compte instanceof ExpertAgricole expert) {
			if (expert.getStatutValidation() != StatutValidation.VALIDE) {
				throw new ResponseStatusException(HttpStatus.CONFLICT,
						"Impossible de lever la suspension d'un expert non validé");
			}
		}

		// 4. Réactiver le compte
		compte.setStatutCompte(StatutCompte.ACTIF);
		personneRepository.save(compte);
	}

}
