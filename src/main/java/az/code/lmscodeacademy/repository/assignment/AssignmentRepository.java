package az.code.lmscodeacademy.repository.assignment;

import az.code.lmscodeacademy.entity.assignment.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
}
