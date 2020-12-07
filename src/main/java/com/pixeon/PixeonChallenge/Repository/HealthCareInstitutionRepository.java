package com.pixeon.PixeonChallenge.Repository;

import com.pixeon.PixeonChallenge.Model.HealthCareInstitution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HealthCareInstitutionRepository extends JpaRepository<HealthCareInstitution, Long> {
    Optional<HealthCareInstitution> findByCnpj(String cnpj);
}
