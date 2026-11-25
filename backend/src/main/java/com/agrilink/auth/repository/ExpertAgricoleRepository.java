package com.agrilink.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.agrilink.auth.entity.ExpertAgricole;

public interface ExpertAgricoleRepository
        extends JpaRepository<ExpertAgricole, Long> {

}
