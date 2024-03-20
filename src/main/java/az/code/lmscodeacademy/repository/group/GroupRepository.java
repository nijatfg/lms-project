package az.code.lmscodeacademy.repository.group;

import az.code.lmscodeacademy.entity.group.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
