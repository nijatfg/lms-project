package az.code.lmscodeacademy.entity.material;

import az.code.lmscodeacademy.entity.group.Group;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "materials")
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String type;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    Group group;
}
