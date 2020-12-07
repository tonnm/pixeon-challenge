package com.pixeon.PixeonChallenge.Service;

import com.pixeon.PixeonChallenge.DTO.ExamDTO;
import com.pixeon.PixeonChallenge.Model.Exam;
import com.pixeon.PixeonChallenge.Model.HealthCareInstitution;
import com.pixeon.PixeonChallenge.Repository.ExamRepository;
import com.pixeon.PixeonChallenge.Utils.DataResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.pixeon.PixeonChallenge.Utils.Constants.*;

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
                examDataResponse.setMessage(EXAM_CREATED);
            } else {
                examDataResponse.setMessage(INSUFFICIENT_PIXEON_CURRENCY);
                return ResponseEntity.status(HttpStatus.OK).body(examDataResponse);
            }
        } else {
            examDataResponse.setMessage(HEALTHCARE_NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(examDataResponse);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(examDataResponse);
    }

    public ResponseEntity<DataResponse<ExamDTO>> retrieveExam(Long idExam, String cnpjHealthCareInstitution) {
        ResponseEntity<DataResponse<ExamDTO>> examResponse = findExamById(idExam);
        DataResponse<ExamDTO> examDataResponse = new DataResponse<>();
        if (Objects.requireNonNull(examResponse.getBody()).getData() != null) {
            ExamDTO examDTO = examResponse.getBody().getData();
            if (examDTO.getHealthCareInstitution().getCnpj().equals(cnpjHealthCareInstitution)) {
                ModelMapper modelMapper = new ModelMapper();
                if (!examDTO.getIsRetrieved()) {
                    healthCareInstitutionService.removePixeonCoin(cnpjHealthCareInstitution);
                    examDTO.setIsRetrieved(true);
                    Exam exam = modelMapper.map(examDTO, Exam.class);
                    examRepository.save(exam);
                }
                examDataResponse.setMessage(EXAM_RETRIEVED);
                examDataResponse.setData(examDTO);
                return ResponseEntity.status(HttpStatus.OK).body(examDataResponse);
            } else {
                examDataResponse.setMessage(HEALTHCARE_UNAUTHORIZED);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(examDataResponse);
            }
        }
        examDataResponse.setMessage(EXAM_NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(examDataResponse);
    }

    public ResponseEntity<DataResponse<ExamDTO>> findExamById(Long id) {
        DataResponse<ExamDTO> examDTODataResponse = new DataResponse<>();
        Exam exam = examRepository.findById(id).orElse(null);
        if (exam != null) {
            examDTODataResponse.setMessage(EXAM_FOUND);
            ModelMapper modelMapper = new ModelMapper();
            ExamDTO examDTO = modelMapper.map(exam, ExamDTO.class);
            examDTODataResponse.setData(examDTO);
            return ResponseEntity.status(HttpStatus.OK).body(examDTODataResponse);
        }
        examDTODataResponse.setMessage(EXAM_NOT_FOUND);
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
            examDTODataResponse.setMessage(EXAM_UPDATED);
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
