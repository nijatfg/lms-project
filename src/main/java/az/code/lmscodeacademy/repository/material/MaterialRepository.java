package az.code.lmscodeacademy.repository.material;

import az.code.lmscodeacademy.entity.material.Material;
import az.code.lmscodeacademy.entity.participation.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaterialRepository extends JpaRepository<Material, Long> {

    Material findByContent(String content);
    List<Material> findByGroupId(Long groupId);
}
