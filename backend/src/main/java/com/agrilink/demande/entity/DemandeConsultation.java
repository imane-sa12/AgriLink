package com.agrilink.demande.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.agrilink.auth.entity.Agriculteur;
import com.agrilink.demande.enums.StatutDemande;
import com.agrilink.expert.entity.Specialite;

import jakarta.persistence.*;

@Entity
@Table(name = "DEMANDE_CONSULTATION")
public class DemandeConsultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_demande")
    private Long idDemande;

    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation = LocalDateTime.now();

    @Column(name = "type_probleme", nullable = false, length = 150)
    private String typeProbleme;

    @Column(name = "culture", nullable = false, length = 100)
    private String culture;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "date_apparition", nullable = false)
    private LocalDate dateApparition;

    @Column(name = "date_aggravation")
    private LocalDate dateAggravation;

    @Column(name = "localisation", nullable = false, length = 255)
    private String localisation;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false, length = 40)
    private StatutDemande statut = StatutDemande.SUBMITTED;

    // Une demande appartient à un seul agriculteur.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_agriculteur", nullable = false)
    private Agriculteur agriculteur;

    // La spécialité sera déterminée par le système.
    // Elle peut donc être absente lors de la création initiale.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_specialite")
    private Specialite specialite;

    // Une nouvelle demande peut faire référence à une demande clôturée.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_demande_precedente")
    private DemandeConsultation demandePrecedente;

    public DemandeConsultation() {
    }

    public Long getIdDemande() {
        return idDemande;
    }

    public void setIdDemande(Long idDemande) {
        this.idDemande = idDemande;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getTypeProbleme() {
        return typeProbleme;
    }

    public void setTypeProbleme(String typeProbleme) {
        this.typeProbleme = typeProbleme;
    }

    public String getCulture() {
        return culture;
    }

    public void setCulture(String culture) {
        this.culture = culture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateApparition() {
        return dateApparition;
    }

    public void setDateApparition(LocalDate dateApparition) {
        this.dateApparition = dateApparition;
    }

    public LocalDate getDateAggravation() {
        return dateAggravation;
    }

    public void setDateAggravation(LocalDate dateAggravation) {
        this.dateAggravation = dateAggravation;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public StatutDemande getStatut() {
        return statut;
    }

    public void setStatut(StatutDemande statut) {
        this.statut = statut;
    }

    public Agriculteur getAgriculteur() {
        return agriculteur;
    }

    public void setAgriculteur(Agriculteur agriculteur) {
        this.agriculteur = agriculteur;
    }

    public Specialite getSpecialite() {
        return specialite;
    }

    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }

    public DemandeConsultation getDemandePrecedente() {
        return demandePrecedente;
    }

    public void setDemandePrecedente(DemandeConsultation demandePrecedente) {
        this.demandePrecedente = demandePrecedente;
    }
}