package com.pixeon.HealthCareInstitution.Controller;

import com.pixeon.HealthCareInstitution.Model.HealthCareInstitution;
import com.pixeon.HealthCareInstitution.Service.HealthCareInstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public HealthCareInstitution create(@RequestBody HealthCareInstitution healthCareInstitution) {
        return healthCareInstitutionService.createHealthCareInstitution(healthCareInstitution);
    }
}

