package com.agrilink.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agrilink.auth.entity.ExpertAgricole;
import com.agrilink.auth.enums.StatutValidation;

public interface ExpertAgricoleRepository
        extends JpaRepository<ExpertAgricole, Long> {

    List<ExpertAgricole> findByStatutValidation(
            StatutValidation statutValidation
    );
}
