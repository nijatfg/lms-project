package az.code.lmscodeacademy.repository.lesson;

import az.code.lmscodeacademy.entity.lesson.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByDate(LocalDate date);

}
