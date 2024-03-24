package az.code.lmscodeacademy.repository.group;

import az.code.lmscodeacademy.entity.group.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByName(String groupName);
}
