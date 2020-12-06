package com.pixeon.HealthCareInstitution.DTO;

import com.pixeon.HealthCareInstitution.Model.HealthCareInstitution;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExamDTO {
    private Long id;

    private String patientName;
    private Long patientAge;
    private String patientGender;
    private HealthCareInstitution healthCareInstitution;
}
