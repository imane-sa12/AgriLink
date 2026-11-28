package com.agrilink.demande.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;

import com.agrilink.demande.entity.DemandeConsultation;
import com.agrilink.demande.enums.StatutDemande;

public interface DemandeConsultationRepository
        extends JpaRepository<DemandeConsultation, Long> {

    List<DemandeConsultation>
    findByAgriculteurIdUtilisateurOrderByDateCreationDesc(Long idAgriculteur);

    List<DemandeConsultation>
    findByStatutOrderByDateCreationDesc(StatutDemande statut);

    List<DemandeConsultation>
    findAllByOrderByDateCreationDesc();

    boolean existsBySpecialiteIdSpecialite(Long idSpecialite);

    // Verrouille la demande pendant un changement de statut
    // pour éviter deux modifications simultanées.
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT d FROM DemandeConsultation d WHERE d.idDemande = :id")
    Optional<DemandeConsultation> findByIdForUpdate(@Param("id") Long id);
}