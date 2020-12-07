package com.pixeon.PixeonChallenge.Service;

import com.pixeon.PixeonChallenge.DTO.HealthCareInstitutionDTO;
import com.pixeon.PixeonChallenge.Model.HealthCareInstitution;
import com.pixeon.PixeonChallenge.Repository.HealthCareInstitutionRepository;
import com.pixeon.PixeonChallenge.Utils.Constants;
import com.pixeon.PixeonChallenge.Utils.DataResponse;
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
        dataResponse.setMessage(Constants.HEALTHCARE_CREATED);
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
