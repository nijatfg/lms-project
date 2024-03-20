package az.code.lmscodeacademy.repository;

import az.code.lmscodeacademy.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
}
