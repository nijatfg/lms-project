package az.code.lmscodeacademy.repository.lesson;

import az.code.lmscodeacademy.entity.lesson.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
}
