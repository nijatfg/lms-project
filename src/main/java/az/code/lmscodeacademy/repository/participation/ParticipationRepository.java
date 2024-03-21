package az.code.lmscodeacademy.repository.participation;

import az.code.lmscodeacademy.entity.participation.Participation;
import az.code.lmscodeacademy.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {

    List<Participation> findByUser(User student);
}
