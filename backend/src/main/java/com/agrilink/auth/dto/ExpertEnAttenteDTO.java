
package com.agrilink.auth.dto;

import com.agrilink.auth.entity.ExpertAgricole;

public class ExpertEnAttenteDTO {

    private Long idUtilisateur;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String bio;

    public ExpertEnAttenteDTO(ExpertAgricole expert) {
        this.idUtilisateur = expert.getIdUtilisateur();
        this.nom = expert.getNom();
        this.prenom = expert.getPrenom();
        this.email = expert.getEmail();
        this.telephone = expert.getTelephone();
        this.bio = expert.getBio();
    }

    public Long getIdUtilisateur() {
        return idUtilisateur;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getBio() {
        return bio;
    }
}
