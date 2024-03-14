package az.code.lmscodeacademy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private LocalDate lessonDate;
    private boolean attendance;

    @Column(name = "participation_data", columnDefinition = "TEXT")
    private String participationData;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
