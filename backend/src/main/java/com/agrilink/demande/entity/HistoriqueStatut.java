package com.agrilink.demande.entity;

import java.time.LocalDateTime;

import com.agrilink.auth.entity.Personne;
import com.agrilink.demande.enums.StatutDemande;

import jakarta.persistence.*;

@Entity
@Table(name = "HISTORIQUE_STATUT")
public class HistoriqueStatut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historique")
    private Long idHistorique;

    // Null uniquement pour le premier statut de la demande.
    @Enumerated(EnumType.STRING)
    @Column(name = "ancien_statut", length = 40)
    private StatutDemande ancienStatut;

    @Enumerated(EnumType.STRING)
    @Column(name = "nouveau_statut", nullable = false, length = 40)
    private StatutDemande nouveauStatut;

    @Column(name = "date_changement", nullable = false, updatable = false)
    private LocalDateTime dateChangement = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_demande", nullable = false)
    private DemandeConsultation demande;

    // Null lorsque le changement est effectué automatiquement par le système.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_auteur")
    private Personne auteur;

    public HistoriqueStatut() {
    }

    public Long getIdHistorique() {
        return idHistorique;
    }

    public void setIdHistorique(Long idHistorique) {
        this.idHistorique = idHistorique;
    }

    public StatutDemande getAncienStatut() {
        return ancienStatut;
    }

    public void setAncienStatut(StatutDemande ancienStatut) {
        this.ancienStatut = ancienStatut;
    }

    public StatutDemande getNouveauStatut() {
        return nouveauStatut;
    }

    public void setNouveauStatut(StatutDemande nouveauStatut) {
        this.nouveauStatut = nouveauStatut;
    }

    public LocalDateTime getDateChangement() {
        return dateChangement;
    }

    public void setDateChangement(LocalDateTime dateChangement) {
        this.dateChangement = dateChangement;
    }

    public DemandeConsultation getDemande() {
        return demande;
    }

    public void setDemande(DemandeConsultation demande) {
        this.demande = demande;
    }

    public Personne getAuteur() {
        return auteur;
    }

    public void setAuteur(Personne auteur) {
        this.auteur = auteur;
    }
}