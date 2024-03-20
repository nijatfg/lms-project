package az.code.lmscodeacademy.repository.material;

import az.code.lmscodeacademy.entity.material.Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Material, Long> {

    Material findByContent(String content);
}
