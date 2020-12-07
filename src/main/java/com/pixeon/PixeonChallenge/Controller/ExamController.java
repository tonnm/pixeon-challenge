package com.pixeon.PixeonChallenge.Controller;

import com.pixeon.PixeonChallenge.DTO.ExamDTO;
import com.pixeon.PixeonChallenge.Model.Exam;
import com.pixeon.PixeonChallenge.Service.ExamService;
import com.pixeon.PixeonChallenge.Utils.DataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/exam"})
public class ExamController {
    private final ExamService examService;

    @Autowired
    public ExamController(ExamService examService) {
        this.examService = examService;
    }


    @PostMapping("/create")
    public ResponseEntity<DataResponse<ExamDTO>> createExam(@RequestBody ExamDTO exam) {
        try {
            return examService.createExam(exam);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResponse<ExamDTO>> findExamById(@PathVariable Long id) {
        return examService.findExamById(id);
    }

    @PostMapping("/retrieve-exam/{idExam}/{cnpjHealthCareInstitution}")
    public ResponseEntity<DataResponse<ExamDTO>> retrieveExam(@PathVariable Long idExam, @PathVariable String cnpjHealthCareInstitution) {
        try {
            return examService.retrieveExam(idExam, cnpjHealthCareInstitution);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/update-exam/{id}")
    public ResponseEntity<DataResponse<ExamDTO>> updateExam(@PathVariable Long id, @RequestBody Exam exam) {
        try {
            return examService.updateExam(id, exam);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete-exam/{id}")
    public ResponseEntity<?> deleteExam(@PathVariable Long id) {
        return examService.deleteExam(id);
    }
}
