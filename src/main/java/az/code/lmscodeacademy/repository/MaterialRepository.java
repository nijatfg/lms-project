package az.code.lmscodeacademy.repository;

import az.code.lmscodeacademy.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Material, Long> {

    Material findByContent(String content);
}
