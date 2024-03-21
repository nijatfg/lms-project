package az.code.lmscodeacademy.dto.response.participation;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ParticipationResponse {
    private Long id;

//    private LocalDate lessonDate;
    private boolean attendance;
    private double percentage;
}
