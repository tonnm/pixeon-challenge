package com.pixeon.PixeonChallenge.Repository;

import com.pixeon.PixeonChallenge.Model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
}
