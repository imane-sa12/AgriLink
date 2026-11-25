
package com.agrilink.auth.entity;

import java.time.LocalDateTime;

import com.agrilink.auth.enums.StatutCompte;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
// associer la classe à la table MySQL PERSONNE
@Table(name = "PERSONNE")
@Inheritance(strategy = InheritanceType.JOINED)  // Prépare l'héritage entre Personne et les trois sous-classes
// joined - chaque sous classe possede une cle primaire qui est cle etrangere dans Personne 
public class Personne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Précise les caractéristiques de chaque colonne
    @Column(name = "id_utilisateur")
    private Long idUtilisateur;

    @Column(name = "nom", nullable = false, length = 100)
    private String nom;

    @Column(name = "prenom", nullable = false, length = 100)
    private String prenom;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "mot_de_passe", nullable = false, length = 255)
    private String motDePasse;

    @Column(name = "telephone", nullable = false, length = 20)
    private String telephone;

    @CreationTimestamp
    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    @Enumerated(EnumType.STRING) // enregistrer le nome d'etat
    @Column(
        name = "statut_compte",
        nullable = false,
        columnDefinition = "ENUM('ACTIF','INACTIF','EN_ATTENTE_VALIDATION','SUSPENDU')"
    )
    private StatutCompte statutCompte;

    // Constructeur sans paramètres
    public Personne() {
    }

    // Getters et Setters

    public Long getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public StatutCompte getStatutCompte() {
        return statutCompte;
    }

    public void setStatutCompte(StatutCompte statutCompte) {
        this.statutCompte = statutCompte;
    }
}
