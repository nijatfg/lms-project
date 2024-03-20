package az.code.lmscodeacademy.repository;

import az.code.lmscodeacademy.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
