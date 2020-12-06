package com.pixeon.HealthCareInstitution.Service;

import com.pixeon.HealthCareInstitution.Model.HealthCareInstitution;
import com.pixeon.HealthCareInstitution.Repository.HealthCareInstitutionRepository;
import com.pixeon.HealthCareInstitution.Utils.DataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HealthCareInstitutionService {

    @Autowired
    private HealthCareInstitutionRepository healthCareInstitutionRepository;

    public HealthCareInstitution createHealthCareInstitution(HealthCareInstitution healthCareInstitution) {
        healthCareInstitution.setPixeonCoins(20);
        return healthCareInstitutionRepository.save(healthCareInstitution);
    }

    public HealthCareInstitution findHealthCareInstitutionByCnpj(String cnpj) {
        return healthCareInstitutionRepository.findByCnpj(cnpj).orElse(null);
    }

    public HealthCareInstitution removePixeonCoin(String cnpj) {
        HealthCareInstitution healthCareInstitution = findHealthCareInstitutionByCnpj(cnpj);
        healthCareInstitution.setPixeonCoins(healthCareInstitution.getPixeonCoins() - 1);
        healthCareInstitutionRepository.save(healthCareInstitution);
        return healthCareInstitution;
    }

}
