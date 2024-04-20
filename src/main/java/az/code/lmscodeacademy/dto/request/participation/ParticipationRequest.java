package az.code.lmscodeacademy.dto.request.participation;

import az.code.lmscodeacademy.entity.enums.Attendance;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ParticipationRequest {

    private LocalDate lessonDate;
    private Attendance attendance;
}
