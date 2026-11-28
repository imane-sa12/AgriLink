package com.agrilink.demande.dto;

import java.time.LocalDateTime;

import com.agrilink.demande.entity.HistoriqueStatut;
import com.agrilink.demande.enums.StatutDemande;

/*
 * DTO : Data Transfer Object.
 *
 * Cette classe sert à envoyer les informations de l'historique
 * au client (Postman ou Frontend).
 *
 * On utilise un DTO pour éviter de retourner directement
 * les entités JPA.
 */
public record HistoriqueResponse(

        Long idHistorique,

        // Ancien statut de la demande.
        StatutDemande ancienStatut,

        // Nouveau statut après la modification.
        StatutDemande nouveauStatut,

        // Date et heure du changement.
        LocalDateTime dateChangement,

        // Identifiant de la personne qui a effectué le changement.
        Long auteurId,

        // Nom complet de l'auteur.
        String auteurNom

) {

    /*
     * Cette méthode transforme une entité HistoriqueStatut
     * en objet HistoriqueResponse.
     */
    public static HistoriqueResponse from(HistoriqueStatut h) {

        return new HistoriqueResponse(

                h.getIdHistorique(),

                h.getAncienStatut(),

                h.getNouveauStatut(),

                h.getDateChangement(),

                // Si auteur == null, le changement vient du système.
                h.getAuteur() == null
                        ? null
                        : h.getAuteur().getIdUtilisateur(),

                // Si aucun auteur n'existe, afficher "Système".
                h.getAuteur() == null
                        ? "Système"
                        : h.getAuteur().getPrenom()
                                + " "
                                + h.getAuteur().getNom()
        );
    }
}