package az.code.lmscodeacademy.repository.submission;

import az.code.lmscodeacademy.entity.assignment.Assignment;
import az.code.lmscodeacademy.entity.submission.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    List<Submission> findByAssignment(Assignment assignment);
}
