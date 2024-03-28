package az.code.lmscodeacademy.entity.group;

import az.code.lmscodeacademy.entity.lesson.Lesson;
import az.code.lmscodeacademy.entity.material.Material;
import az.code.lmscodeacademy.entity.participation.Participation;
import az.code.lmscodeacademy.entity.submission.Submission;
import az.code.lmscodeacademy.entity.user.User;
import az.code.lmscodeacademy.entity.assignment.Assignment;
import az.code.lmscodeacademy.entity.course.Course;
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

    @OneToMany(mappedBy = "group")
    private List<Lesson> lessons;

    @OneToMany(mappedBy = "group")
    private List<Participation> participations;

    @OneToMany(mappedBy = "group")
    private List<Submission> submissions;
}
