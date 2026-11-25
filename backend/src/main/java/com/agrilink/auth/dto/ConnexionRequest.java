package com.agrilink.auth.dto;

public class ConnexionRequest {

    private String email;
    private String motDePasse;

    public ConnexionRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
}

/*
 * DTO (Data Transfer Object) :
 * Objet permettant de transférer les données entre le frontend et le backend.
 *
 * Entity : représente les données enregistrées dans MySQL.
 *
 * DTO : représente les données reçues ou envoyées par l'application.
 */