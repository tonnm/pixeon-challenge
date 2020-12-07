package com.pixeon.HealthCareInstitution.Controller;

import com.pixeon.HealthCareInstitution.DTO.HealthCareInstitutionDTO;
import com.pixeon.HealthCareInstitution.Model.HealthCareInstitution;
import com.pixeon.HealthCareInstitution.Service.HealthCareInstitutionService;
import com.pixeon.HealthCareInstitution.Utils.DataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health-care-institution")
public class HealthcareInstitutionController {

    @Autowired
    private HealthCareInstitutionService healthCareInstitutionService;
    
    @PostMapping
    public ResponseEntity<DataResponse<HealthCareInstitutionDTO>> create(@RequestBody HealthCareInstitutionDTO healthCareInstitutionDTO) {
        return healthCareInstitutionService.createHealthCareInstitution(healthCareInstitutionDTO);
    }
}

