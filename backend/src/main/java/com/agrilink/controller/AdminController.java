package com.agrilink.controller;

import java.util.List;
import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.agrilink.auth.service.AdminService;
import com.agrilink.auth.dto.ExpertEnAttenteDTO;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	private final AdminService adminService;

	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}

	// GET : consulter les experts en attente
	@GetMapping("/experts/en-attente")
	public ResponseEntity<List<ExpertEnAttenteDTO>> consulterExpertsEnAttente() {

		return ResponseEntity.ok(adminService.consulterExpertsEnAttente());
	}

	// PUT : valider un expert
	@PutMapping("/experts/{id}/valider")
	public ResponseEntity<String> validerExpert(@PathVariable Long id) {

		adminService.validerExpert(id);

		return ResponseEntity.ok("Le compte expert a été validé avec succès.");
	}

	// PUT : refuser un expert
	@PutMapping("/experts/{id}/refuser")
	public ResponseEntity<String> refuserExpert(@PathVariable Long id) {

		adminService.refuserExpert(id);

		return ResponseEntity.ok("Le compte expert a été refusé.");
	}

	// PUT : désactiver un compte
	@PutMapping("/comptes/{id}/desactiver")
	public ResponseEntity<String> desactiverCompte(@PathVariable Long id, Principal principal) {

		adminService.desactiverCompte(id, principal.getName());

		return ResponseEntity.ok("Compte désactivé avec succès.");
	}

	// PUT : réactiver un compte
	@PutMapping("/comptes/{id}/reactiver")
	public ResponseEntity<String> reactiverCompte(@PathVariable Long id) {

		adminService.reactiverCompte(id);

		return ResponseEntity.ok("Compte réactivé avec succès.");
	}

	// PUT : suspendre un compte
	@PutMapping("/comptes/{id}/suspendre")
	public ResponseEntity<String> suspendreCompte(@PathVariable Long id, Principal principal) {

		adminService.suspendreCompte(id, principal.getName());

		return ResponseEntity.ok("Compte suspendu avec succès.");
	}

	// PUT : lever la suspension d'un compte
	@PutMapping("/comptes/{id}/lever-suspension")
	public ResponseEntity<String> leverSuspension(@PathVariable Long id) {

		adminService.leverSuspension(id);

		return ResponseEntity.ok("Suspension levée avec succès.");
	}

}
