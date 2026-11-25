package com.agrilink.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.agrilink.auth.entity.Administrateur;

public interface AdministrateurRepository
        extends JpaRepository<Administrateur, Long> {

}
