
package com.agrilink.notification.service;

/**
 * Interface définissant le contrat du service de notification.
 *
 * Elle permet aux autres modules d'AgriLink de demander
 * l'envoi d'une notification sans dépendre de son implémentation.
 *
 * L'implémentation sera réalisée dans le module Notification.
 */
public interface NotificationService {

    /**
     * Notifie un utilisateur.
     *
     * Exemples :
     * - Demande créée.
     * - Expert affecté à une demande.
     * - Nouvelle demande à confirmer par l'expert.
     * - Informations complémentaires demandées.
     * - Recommandation disponible.
     *
     * @param userId identifiant de l'utilisateur destinataire
     * @param type type de notification
     * @param message contenu de la notification
     */
    void notifier(Long userId, String type, String message);
}
