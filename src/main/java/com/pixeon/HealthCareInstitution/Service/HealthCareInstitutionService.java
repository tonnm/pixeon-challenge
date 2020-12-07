package com.pixeon.HealthCareInstitution.Service;

import com.pixeon.HealthCareInstitution.DTO.HealthCareInstitutionDTO;
import com.pixeon.HealthCareInstitution.Model.HealthCareInstitution;
import com.pixeon.HealthCareInstitution.Repository.HealthCareInstitutionRepository;
import com.pixeon.HealthCareInstitution.Utils.DataResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class HealthCareInstitutionService {

    @Autowired
    private HealthCareInstitutionRepository healthCareInstitutionRepository;

    public ResponseEntity<DataResponse<HealthCareInstitutionDTO>> createHealthCareInstitution(HealthCareInstitutionDTO healthCareInstitutionDTO) {
        DataResponse<HealthCareInstitutionDTO> dataResponse = new DataResponse<>();
        ModelMapper modelMapper = new ModelMapper();
        healthCareInstitutionDTO.setPixeonCoins(20);
        HealthCareInstitution healthCareInstitution = modelMapper.map(healthCareInstitutionDTO, HealthCareInstitution.class);
        dataResponse.setMessage("Healthcare Institution created with success.");
        healthCareInstitutionRepository.save(healthCareInstitution);
        dataResponse.setData(healthCareInstitutionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(dataResponse);
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
