package com.pixeon.HealthCareInstitution.Service;

import com.pixeon.HealthCareInstitution.DTO.ExamDTO;
import com.pixeon.HealthCareInstitution.Model.Exam;
import com.pixeon.HealthCareInstitution.Model.HealthCareInstitution;
import com.pixeon.HealthCareInstitution.Repository.ExamRepository;
import com.pixeon.HealthCareInstitution.Utils.DataResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ExamService {
    @Autowired
    private ExamRepository examRepository;


    @Autowired
    private HealthCareInstitutionService healthCareInstitutionService;

    public ResponseEntity<DataResponse<ExamDTO>> createExam(ExamDTO examDTO) {
        DataResponse<ExamDTO> examDataResponse = new DataResponse<>();
        ModelMapper modelMapper = new ModelMapper();
        HealthCareInstitution healthCareInstitution = healthCareInstitutionService.findHealthCareInstitutionByCnpj(examDTO.getHealthCareInstitution().getCnpj());
        if (healthCareInstitution != null) {
            if (healthCareInstitution.getPixeonCoins() > 0) {
                examDTO.setHealthCareInstitution(healthCareInstitutionService.removePixeonCoin(healthCareInstitution.getCnpj()));
                Exam exam = modelMapper.map(examDTO, Exam.class);
                exam.setIsRetrieved(false);
                examRepository.save(exam);
                examDataResponse.setData(examDTO);
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

    public ResponseEntity<DataResponse<ExamDTO>> retrieveExam(Long idExam, String cnpjHealthCareInstitution) {
        ResponseEntity<DataResponse<ExamDTO>> examResponse = findExamById(idExam);
        ExamDTO examDTO = examResponse.getBody().getData();
        DataResponse<ExamDTO> examDataResponse = new DataResponse<>();
        if (examDTO != null) {
            if (examDTO.getHealthCareInstitution().getCnpj().equals(cnpjHealthCareInstitution)) {
                ModelMapper modelMapper = new ModelMapper();
                if (!examDTO.getIsRetrieved()) {
                    healthCareInstitutionService.removePixeonCoin(cnpjHealthCareInstitution);
                    examDTO.setIsRetrieved(true);
                    Exam exam = modelMapper.map(examDTO, Exam.class);
                    examRepository.save(exam);
                }
                examDataResponse.setMessage("Exam retrieved with success.");
                examDataResponse.setData(examDTO);
                return ResponseEntity.status(HttpStatus.OK).body(examDataResponse);
            } else {
                examDataResponse.setMessage("This Healthcare Institution don't have permission to access this exam.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(examDataResponse);
            }
        }
        examDataResponse.setMessage("Exam not found.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(examDataResponse);
    }

    public ResponseEntity<DataResponse<ExamDTO>> findExamById(Long id) {
        DataResponse<ExamDTO> examDTODataResponse = new DataResponse<>();
        Exam exam = examRepository.findById(id).orElse(null);
        if (exam != null) {
            examDTODataResponse.setMessage("Exam found with success.");
            ModelMapper modelMapper = new ModelMapper();
            ExamDTO examDTO = modelMapper.map(exam, ExamDTO.class);
            examDTODataResponse.setData(examDTO);
            return ResponseEntity.status(HttpStatus.OK).body(examDTODataResponse);
        }
        examDTODataResponse.setMessage("Exam not found.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(examDTODataResponse);
    }

    public ResponseEntity<DataResponse<ExamDTO>> updateExam(Long id, Exam exam) {
        DataResponse<ExamDTO> examDTODataResponse = new DataResponse<>();
        return examRepository.findById(id).map(record -> {
            record.setPatientName(exam.getPatientName());
            record.setPatientAge(exam.getPatientAge());
            record.setPatientGender(exam.getPatientGender());
            ModelMapper modelMapper = new ModelMapper();
            ExamDTO examUpdated = modelMapper.map(examRepository.save(record), ExamDTO.class);
            examDTODataResponse.setData(examUpdated);
            examDTODataResponse.setMessage("Exam updated with success.");
            return ResponseEntity.status(HttpStatus.OK).body(examDTODataResponse);
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(examDTODataResponse));
    }

    public ResponseEntity<?> deleteExam(Long id) {
        return examRepository.findById(id).map(record -> {
            examRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
