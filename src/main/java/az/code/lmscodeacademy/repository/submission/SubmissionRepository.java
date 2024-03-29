package az.code.lmscodeacademy.repository.submission;

import az.code.lmscodeacademy.entity.assignment.Assignment;
import az.code.lmscodeacademy.entity.group.Group;
import az.code.lmscodeacademy.entity.submission.Submission;
import az.code.lmscodeacademy.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    List<Submission> findByAssignmentAndGroup(Assignment assignment, Group group);

    List<Submission> findByAssignmentAndGroupAndUser(Assignment assignment, Group group, User user);

}
