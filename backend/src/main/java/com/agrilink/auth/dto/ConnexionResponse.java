package com.agrilink.auth.dto;

public class ConnexionResponse {

    private Long idUtilisateur;
    private String nom;
    private String prenom;
    private String email;
    private String role;
    private String token;

    public ConnexionResponse() {
    }

    public ConnexionResponse(Long idUtilisateur, String nom,
            String prenom, String email, String role, String token) {
        this.idUtilisateur = idUtilisateur;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.role = role;
        this.token = token;
    }

    public Long getIdUtilisateur() { return idUtilisateur; }
    public void setIdUtilisateur(Long idUtilisateur) { this.idUtilisateur = idUtilisateur; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}