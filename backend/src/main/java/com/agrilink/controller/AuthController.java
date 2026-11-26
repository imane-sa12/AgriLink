package com.agrilink.controller;

import org.springframework.http.HttpStatus;
import com.agrilink.auth.dto.ConnexionRequest;
import com.agrilink.auth.dto.ConnexionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.agrilink.auth.dto.InscriptionAgriculteurRequest;
import com.agrilink.auth.service.AuthService;

@RestController
@RequestMapping("/api/auth")
// le chemin commun des routes d'euthentification
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	// Inscription d'un agriculteur
	@PostMapping("/inscription/agriculteur")
	public ResponseEntity<String> inscrireAgriculteur(@RequestBody InscriptionAgriculteurRequest request) {

		authService.inscrireAgriculteur(request);

		return ResponseEntity.status(HttpStatus.CREATED).body("Inscription de l'agriculteur réussie !");
	}

	@PostMapping("/connexion")
	public ResponseEntity<ConnexionResponse> connecter(@RequestBody ConnexionRequest request) {

		ConnexionResponse response = authService.connecter(request);

		return ResponseEntity.ok(response);
	}
}
