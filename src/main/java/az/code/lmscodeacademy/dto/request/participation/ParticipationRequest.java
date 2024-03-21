package az.code.lmscodeacademy.dto.request.participation;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ParticipationRequest {

    private LocalDate lessonDate;
    private boolean attendance;
}
