package com.pixeon.PixeonChallenge.DTO;

import com.pixeon.PixeonChallenge.Model.HealthCareInstitution;
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
    private Boolean isRetrieved;
    private String physicianName;
    private String physicianCRM;
    private String ProcedureName;

    private HealthCareInstitution healthCareInstitution;
}
