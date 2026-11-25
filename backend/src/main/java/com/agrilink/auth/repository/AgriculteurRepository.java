package com.agrilink.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.agrilink.auth.entity.Agriculteur;

public interface AgriculteurRepository
        extends JpaRepository<Agriculteur, Long> {

}
