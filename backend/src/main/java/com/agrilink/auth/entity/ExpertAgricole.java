
package com.agrilink.auth.entity;

import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import com.agrilink.auth.enums.StatutValidation;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Column;


@Entity
@Table(name = "expert_agricole")
@PrimaryKeyJoinColumn(name = "id_utilisateur")
public class ExpertAgricole extends Personne {

    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut_validation", nullable = false, length = 30)
    private StatutValidation statutValidation;

    @Column(name = "disponible", nullable = false)
    private boolean disponible = true;

    @Column(name = "charge_actuelle", nullable = false)
    private Integer chargeActuelle = 0;

    public ExpertAgricole() {
    }

    // Getters et Setters

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public StatutValidation getStatutValidation() {
        return statutValidation;
    }

    public void setStatutValidation(StatutValidation statutValidation) {
        this.statutValidation = statutValidation;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Integer getChargeActuelle() {
        return chargeActuelle;
    }

    public void setChargeActuelle(Integer chargeActuelle) {
        this.chargeActuelle = chargeActuelle;
    }
}
