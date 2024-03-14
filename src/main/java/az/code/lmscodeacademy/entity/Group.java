package az.code.lmscodeacademy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "group")
    private List<User> user = new ArrayList<>();

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    Course course;

    @OneToMany(mappedBy = "group")
    private List<Material> materials = new ArrayList<>();

    @OneToMany(mappedBy = "group")
    private List<Assignment> assignments;
}
