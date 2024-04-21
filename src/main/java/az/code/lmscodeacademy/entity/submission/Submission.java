package az.code.lmscodeacademy.entity.submission;

import az.code.lmscodeacademy.entity.group.Group;
import az.code.lmscodeacademy.entity.user.User;
import az.code.lmscodeacademy.entity.assignment.Assignment;
import az.code.lmscodeacademy.entity.grade.Grade;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "submissions")
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String link;

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    private User user;

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    private Assignment assignment;

    @OneToOne(mappedBy = "submission",cascade = CascadeType.REMOVE)
    @JsonIgnore
    @ToString.Exclude
    private Grade grade;

    @ManyToOne
    @JoinColumn(name = "group_id")
    @JsonIgnore
    @ToString.Exclude
    Group group;
}
