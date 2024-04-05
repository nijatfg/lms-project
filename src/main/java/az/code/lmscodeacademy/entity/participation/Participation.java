package az.code.lmscodeacademy.entity.participation;

import az.code.lmscodeacademy.entity.group.Group;
import az.code.lmscodeacademy.entity.user.User;
import az.code.lmscodeacademy.entity.lesson.Lesson;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "participations")
public class Participation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    private LocalDate lessonDate;
    private boolean attendance;

    @Column(name = "participation_data", columnDefinition = "TEXT")
    private String participationData;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    @ToString.Exclude
    private User user;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    @JsonIgnore
    @ToString.Exclude
    private Lesson lesson;

    @ManyToOne
    @JoinColumn(name = "group_id")
    @JsonIgnore
    @ToString.Exclude
    private Group group;


}
