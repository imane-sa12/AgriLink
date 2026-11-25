package com.agrilink.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> inscrireAgriculteur(
            @RequestBody InscriptionAgriculteurRequest request) {

        authService.inscrireAgriculteur(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Inscription de l'agriculteur réussie !");
    }
}
