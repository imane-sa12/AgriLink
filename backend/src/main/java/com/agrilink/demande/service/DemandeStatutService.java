package com.agrilink.demande.service;

import com.agrilink.demande.dto.HistoriqueResponse;
import com.agrilink.demande.entity.DemandeConsultation;
import com.agrilink.demande.entity.HistoriqueStatut;
import com.agrilink.demande.enums.StatutDemande;
import com.agrilink.demande.repository.DemandeConsultationRepository;
import com.agrilink.demande.repository.HistoriqueStatutRepository;
import com.agrilink.auth.entity.Personne;
import com.agrilink.auth.repository.PersonneRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Service responsable de la gestion des statuts des demandes.
 *
 * Il permet :
 * - de contrôler les transitions autorisées ;
 * - de modifier le statut d'une demande ;
 * - d'enregistrer chaque changement dans l'historique ;
 * - de consulter l'historique d'une demande.
 */
@Service
public class DemandeStatutService {

    private final DemandeConsultationRepository demandeRepository;
    private final HistoriqueStatutRepository historiqueRepository;
    private final PersonneRepository personneRepository;

    /**
     * Constructeur avec injection des dépendances.
     */
    public DemandeStatutService(
            DemandeConsultationRepository demandeRepository,
            HistoriqueStatutRepository historiqueRepository,
            PersonneRepository personneRepository) {

        this.demandeRepository = demandeRepository;
        this.historiqueRepository = historiqueRepository;
        this.personneRepository = personneRepository;
    }

    /**
     * Définition des transitions autorisées entre les statuts.
     *
     * Les statuts terminaux CLOSED et CANCELLED ne peuvent plus évoluer.
     *
     * Important :
     * Une demande ne peut passer à ASSIGNED qu'après la confirmation
     * de l'expert, conformément à la règle RG13.
     */
    private static final Map<StatutDemande, Set<StatutDemande>> TRANSITIONS =
            Map.ofEntries(

                    // Demande créée : le système cherche un expert.
                    Map.entry(
                            StatutDemande.SUBMITTED,
                            EnumSet.of(
                                    StatutDemande.EN_ATTENTE_D_ATTRIBUTION,
                                    StatutDemande.EN_ATTENTE_CONFIRMATION,
                                    StatutDemande.CANCELLED
                            )
                    ),

                    // Aucun expert disponible pour le moment.
                    Map.entry(
                            StatutDemande.EN_ATTENTE_D_ATTRIBUTION,
                            EnumSet.of(
                                    StatutDemande.EN_ATTENTE_CONFIRMATION,
                                    StatutDemande.CANCELLED
                            )
                    ),

                    // Un expert a été sélectionné.
                    // Il doit confirmer ou refuser l'affectation.
                    Map.entry(
                            StatutDemande.EN_ATTENTE_CONFIRMATION,
                            EnumSet.of(
                                    StatutDemande.ASSIGNED,
                                    StatutDemande.EN_ATTENTE_D_ATTRIBUTION,
                                    StatutDemande.CANCELLED
                            )
                    ),

                    // L'expert a confirmé l'affectation.
                    // Il peut commencer l'analyse.
                    Map.entry(
                            StatutDemande.ASSIGNED,
                            EnumSet.of(
                                    StatutDemande.IN_ANALYSIS,
                                    StatutDemande.CANCELLED
                            )
                    ),

                    // L'expert analyse le dossier.
                    Map.entry(
                            StatutDemande.IN_ANALYSIS,
                            EnumSet.of(
                                    StatutDemande.WAITING_FOR_FARMER,
                                    StatutDemande.ADVICE_PROVIDED,
                                    StatutDemande.CANCELLED
                            )
                    ),

                    // L'expert attend des informations complémentaires.
                    Map.entry(
                            StatutDemande.WAITING_FOR_FARMER,
                            EnumSet.of(
                                    StatutDemande.IN_ANALYSIS,
                                    StatutDemande.CANCELLED
                            )
                    ),

                    // Une recommandation a été transmise.
                    // La demande peut maintenant être clôturée.
                    Map.entry(
                            StatutDemande.ADVICE_PROVIDED,
                            EnumSet.of(
                                    StatutDemande.CLOSED
                            )
                    ),

                    // Une demande clôturée est définitive.
                    Map.entry(
                            StatutDemande.CLOSED,
                            EnumSet.noneOf(StatutDemande.class)
                    ),

                    // Une demande annulée est définitive.
                    Map.entry(
                            StatutDemande.CANCELLED,
                            EnumSet.noneOf(StatutDemande.class)
                    )
            );

    /**
     * Modifie le statut d'une demande et enregistre le changement
     * dans l'historique.
     *
     * @param demandeId identifiant de la demande
     * @param nouveauStatut nouveau statut souhaité
     * @param auteurId identifiant de la personne qui effectue l'action
     */
    @Transactional
    public void changer(
            Long demandeId,
            StatutDemande nouveauStatut,
            Long auteurId) {

        // Vérifier les paramètres.
        if (demandeId == null) {
            throw new IllegalArgumentException(
                    "L'identifiant de la demande est obligatoire."
            );
        }

        if (nouveauStatut == null) {
            throw new IllegalArgumentException(
                    "Le nouveau statut est obligatoire."
            );
        }

        // Récupérer la demande avec un verrou pour éviter
        // les modifications simultanées non contrôlées.
        DemandeConsultation demande = demandeRepository
                .findByIdForUpdate(demandeId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Demande introuvable : " + demandeId
                ));

        StatutDemande ancienStatut = demande.getStatut();

        if (ancienStatut == null) {
            throw new IllegalStateException(
                    "La demande ne possède pas de statut actuel."
            );
        }

        // Éviter d'enregistrer un changement inutile.
        if (ancienStatut == nouveauStatut) {
            return;
        }

        // Vérifier que la transition est autorisée.
        Set<StatutDemande> statutsAutorises =
                TRANSITIONS.getOrDefault(
                        ancienStatut,
                        Set.of()
                );

        if (!statutsAutorises.contains(nouveauStatut)) {
            throw new IllegalStateException(
                    "Transition de statut interdite : "
                            + ancienStatut
                            + " -> "
                            + nouveauStatut
            );
        }

        // Modifier et sauvegarder le statut de la demande.
        demande.setStatut(nouveauStatut);
        demandeRepository.save(demande);

        // Enregistrer le changement dans l'historique.
        enregistrer(
                demande,
                ancienStatut,
                nouveauStatut,
                auteurId
        );
    }

    /**
     * Enregistre le statut initial d'une demande dans l'historique.
     *
     * Cette méthode doit être appelée par le service de création
     * après l'enregistrement de la nouvelle demande en base de données.
     *
     * @param demande demande nouvellement créée
     * @param auteurId identifiant de l'agriculteur créateur
     */
    @Transactional
    public void enregistrerCreation(
            DemandeConsultation demande,
            Long auteurId) {

        if (demande == null || demande.getIdDemande() == null) {
            throw new IllegalArgumentException(
                    "La demande doit être enregistrée avant de créer son historique."
            );
        }

        if (demande.getStatut() != StatutDemande.SUBMITTED) {
            throw new IllegalStateException(
                    "Une nouvelle demande doit commencer avec le statut SUBMITTED."
            );
        }

        // Le statut précédent est null puisqu'il s'agit
        // du premier enregistrement de la demande.
        enregistrer(
                demande,
                null,
                StatutDemande.SUBMITTED,
                auteurId
        );
    }

    /**
     * Consulte l'historique des changements de statut d'une demande.
     *
     * @param demandeId identifiant de la demande
     * @return historique trié par date croissante
     */
    @Transactional(readOnly = true)
    public List<HistoriqueResponse> historique(Long demandeId) {

        if (demandeId == null) {
            throw new IllegalArgumentException(
                    "L'identifiant de la demande est obligatoire."
            );
        }

        // Vérifier que la demande existe.
        if (!demandeRepository.existsById(demandeId)) {
            throw new EntityNotFoundException(
                    "Demande introuvable : " + demandeId
            );
        }

        // Récupérer les événements de l'historique
        // dans l'ordre chronologique.
        return historiqueRepository
                .findByDemandeIdDemandeOrderByDateChangementAscIdHistoriqueAsc(
                        demandeId
                )
                .stream()
                .map(HistoriqueResponse::from)
                .toList();
    }

    /**
     * Méthode privée qui crée et sauvegarde une entrée d'historique.
     *
     * @param demande demande concernée
     * @param ancienStatut ancien statut (null lors de la création)
     * @param nouveauStatut nouveau statut
     * @param auteurId personne responsable du changement
     */
    private void enregistrer(
            DemandeConsultation demande,
            StatutDemande ancienStatut,
            StatutDemande nouveauStatut,
            Long auteurId) {

        HistoriqueStatut historique = new HistoriqueStatut();

        historique.setDemande(demande);
        historique.setAncienStatut(ancienStatut);
        historique.setNouveauStatut(nouveauStatut);

        // Si auteurId est null, le changement est effectué
        // par le système (exemple : affectation automatique).
        if (auteurId != null) {

            Personne auteur = personneRepository
                    .findById(auteurId)
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Auteur introuvable : " + auteurId
                    ));

            historique.setAuteur(auteur);
        }

        historiqueRepository.save(historique);
    }
}
