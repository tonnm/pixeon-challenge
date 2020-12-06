package com.pixeon.HealthCareInstitution.Repository;

import com.pixeon.HealthCareInstitution.Model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
}
