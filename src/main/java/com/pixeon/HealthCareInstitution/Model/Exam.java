package com.pixeon.HealthCareInstitution.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String patientName;
    private Long patientAge;
    private String patientGender;
    private Boolean isRetrieved;
    private String physicianName;
    private String physicianCRM;
    private String ProcedureName;

    @JoinColumn
    @OneToOne(cascade = CascadeType.PERSIST)
    private HealthCareInstitution healthCareInstitution;


}
