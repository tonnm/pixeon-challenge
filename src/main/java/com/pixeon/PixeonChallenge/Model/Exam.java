package com.pixeon.PixeonChallenge.Model;

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

    @Column
    private String patientName;

    @Column
    private Long patientAge;

    @Column
    private String patientGender;

    @Column
    private Boolean isRetrieved;

    @Column
    private String physicianName;

    @Column
    private String physicianCRM;

    @Column
    private String ProcedureName;

    @JoinColumn
    @OneToOne(cascade = CascadeType.PERSIST)
    private HealthCareInstitution healthCareInstitution;


}
