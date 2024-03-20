package az.code.lmscodeacademy.repository.course;

import az.code.lmscodeacademy.entity.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
