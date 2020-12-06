package com.pixeon.HealthCareInstitution.Service;

import com.pixeon.HealthCareInstitution.Model.Exam;
import com.pixeon.HealthCareInstitution.Model.HealthCareInstitution;
import com.pixeon.HealthCareInstitution.Repository.ExamRepository;
import com.pixeon.HealthCareInstitution.Utils.DataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamService {
    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private HealthCareInstitutionService healthCareInstitutionService;

    public ResponseEntity<DataResponse<Exam>> createExam(Exam exam) {
        DataResponse<Exam> examDataResponse = new DataResponse<>();
        HealthCareInstitution healthCareInstitution = healthCareInstitutionService.findHealthCareInstitutionByCnpj(exam.getHealthCareInstitution().getCnpj());
        if (healthCareInstitution != null) {
            if (healthCareInstitution.getPixeonCoins() > 0) {
                exam.setHealthCareInstitution(healthCareInstitutionService.removePixeonCoin(healthCareInstitution.getCnpj()));
                exam.setIsRetrieved(false);
                examRepository.save(exam);
                examDataResponse.setData(exam);
                examDataResponse.setMessage("Exam created with success.");
            } else {
                examDataResponse.setMessage("This Healthcare Institution don't have enough Pixeon coin.");
                return ResponseEntity.status(HttpStatus.OK).body(examDataResponse);
            }
        } else {
            examDataResponse.setMessage("HealthCare Institution not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(examDataResponse);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(examDataResponse);
    }

    public ResponseEntity<DataResponse<Exam>> retrieveExam(Long idExam, String cnpjHealthCareInstitution) {
        Exam exam = findExamById(idExam);
        DataResponse<Exam> examDataResponse = new DataResponse<>();
        if (exam != null) {
            if (exam.getHealthCareInstitution().getCnpj().equals(cnpjHealthCareInstitution)) {
                if (!exam.getIsRetrieved()) {
                    healthCareInstitutionService.removePixeonCoin(cnpjHealthCareInstitution);
                    exam.setIsRetrieved(true);
                    examRepository.save(exam);
                }
                examDataResponse.setMessage("Exam retrieved with success.");
                examDataResponse.setData(exam);
                return ResponseEntity.status(HttpStatus.OK).body(examDataResponse);
            } else {
                examDataResponse.setMessage("This Healthcare Institution don't have permission to access this exam.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(examDataResponse);
            }
        }
        examDataResponse.setMessage("Exam not found.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(examDataResponse);
    }

    public Exam findExamById(Long id) {
        return examRepository.findById(id).orElse(null);
    }

    public ResponseEntity<Exam> updateExam(Long id, Exam exam) {
        return examRepository.findById(id).map(record -> {
            record.setPatientName(exam.getPatientName());
            record.setPatientAge(exam.getPatientAge());
            record.setPatientGender(exam.getPatientGender());
            Exam examUpdated = examRepository.save(record);
            return ResponseEntity.ok().body(examUpdated);
        }).orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<?> deleteExam(Long id) {
        return examRepository.findById(id).map(record -> {
            examRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
