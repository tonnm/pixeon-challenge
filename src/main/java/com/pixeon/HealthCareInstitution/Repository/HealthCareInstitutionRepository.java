package com.pixeon.HealthCareInstitution.Repository;

import com.pixeon.HealthCareInstitution.Model.HealthCareInstitution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HealthCareInstitutionRepository extends JpaRepository<HealthCareInstitution, Long> {
    Optional<HealthCareInstitution> findByCnpj(String cnpj);
}
