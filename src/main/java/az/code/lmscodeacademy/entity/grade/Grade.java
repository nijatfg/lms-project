package az.code.lmscodeacademy.entity.grade;

import az.code.lmscodeacademy.entity.submission.Submission;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "grades")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int score;
    private String feedback;

    @OneToOne
    @JoinColumn(name = "submission_id", unique = true)
    private Submission submission;
}
