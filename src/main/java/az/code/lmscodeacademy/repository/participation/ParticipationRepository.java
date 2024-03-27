package az.code.lmscodeacademy.repository.participation;

import az.code.lmscodeacademy.entity.assignment.Assignment;
import az.code.lmscodeacademy.entity.participation.Participation;
import az.code.lmscodeacademy.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {

    List<Participation> findByUser(User student);
    List<Participation> findByUserIdAndLessonDate(Long user_id, LocalDate lessonDate);
    List<Participation> findByGroupId(Long groupId);


}
