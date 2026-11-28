package com.agrilink.expert.entity;

import java.util.HashSet;
import java.util.Set;

import com.agrilink.auth.entity.ExpertAgricole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

/*
 * Cette entité représente les spécialités agricoles.
 *
 * Exemples :
 * - Phytopathologie
 * - Irrigation
 * - Arboriculture
 * - Protection des cultures
 *
 * Une spécialité peut être associée à plusieurs experts.
 * Un expert peut également posséder plusieurs spécialités.
 */
@Entity
@Table(name = "SPECIALITE")
public class Specialite {

    // Identifiant unique de la spécialité.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_specialite")
    private Long idSpecialite;

    // Nom de la spécialité.
    @Column(name = "libelle", nullable = false, length = 100)
    private String libelle;

    // Description facultative de la spécialité.
    @Column(name = "description", length = 500)
    private String description;

    /*
     * Relation plusieurs-à-plusieurs entre ExpertAgricole et Specialite.
     *
     * La table intermédiaire EXPERT_SPECIALITE contient :
     * - id_specialite
     * - id_utilisateur
     */
    @ManyToMany
    @JoinTable(
        name = "EXPERT_SPECIALITE",
        joinColumns = @JoinColumn(name = "id_specialite"),
        inverseJoinColumns = @JoinColumn(name = "id_utilisateur")
    )
    private Set<ExpertAgricole> experts = new HashSet<>();

    // Constructeur vide nécessaire à JPA.
    public Specialite() {
    }

    // Getters et setters.

    public Long getIdSpecialite() {
        return idSpecialite;
    }

    public void setIdSpecialite(Long idSpecialite) {
        this.idSpecialite = idSpecialite;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<ExpertAgricole> getExperts() {
        return experts;
    }

    public void setExperts(Set<ExpertAgricole> experts) {
        this.experts = experts;
    }
}