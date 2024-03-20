package az.code.lmscodeacademy.repository;

import az.code.lmscodeacademy.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
