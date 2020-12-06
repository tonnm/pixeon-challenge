package com.pixeon.HealthCareInstitution.Controller;

import com.pixeon.HealthCareInstitution.Model.Exam;
import com.pixeon.HealthCareInstitution.Service.ExamService;
import com.pixeon.HealthCareInstitution.Utils.DataResponse;
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
    public ResponseEntity<DataResponse<Exam>> createExam(@RequestBody Exam exam) {
        try {
            return examService.createExam(exam);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exam> findExamById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(examService.findExamById(id));
    }

    @PostMapping("/retrieve-exam/{idExam}/{cnpjHealthCareInstitution}")
    public ResponseEntity<DataResponse<Exam>> retrieveExam(@PathVariable Long idExam, @PathVariable String cnpjHealthCareInstitution) {
        try {
            return examService.retrieveExam(idExam, cnpjHealthCareInstitution);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/update-exam/{id}")
    public ResponseEntity<Exam> updateExam(@PathVariable Long id, @RequestBody Exam exam) {
        return examService.updateExam(id, exam);
    }

    @DeleteMapping("/delete-exam/{id}")
    public ResponseEntity<?> deleteExam(@PathVariable Long id) {
        return examService.deleteExam(id);
    }
}
