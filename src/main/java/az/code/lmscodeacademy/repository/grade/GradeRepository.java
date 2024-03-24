package az.code.lmscodeacademy.repository.grade;

import az.code.lmscodeacademy.entity.grade.Grade;
import az.code.lmscodeacademy.entity.submission.Submission;
import az.code.lmscodeacademy.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {

    Grade findBySubmission(Submission submission);
}
