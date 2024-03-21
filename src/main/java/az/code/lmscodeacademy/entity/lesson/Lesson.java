package az.code.lmscodeacademy.entity.lesson;

import az.code.lmscodeacademy.entity.group.Group;
import az.code.lmscodeacademy.entity.participation.Participation;
import az.code.lmscodeacademy.entity.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private LocalDate date;
    private int duration;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "lesson")
    private List<Participation> participations = new ArrayList<>();

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    private Group group;
}
