package com.agrilink.demande.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agrilink.demande.entity.HistoriqueStatut;

public interface HistoriqueStatutRepository
        extends JpaRepository<HistoriqueStatut, Long> {

    List<HistoriqueStatut>
    findByDemandeIdDemandeOrderByDateChangementAscIdHistoriqueAsc(
            Long idDemande
    );
}