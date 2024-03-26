package az.code.lmscodeacademy.dto.response.participation;

import az.code.lmscodeacademy.dto.response.lesson.LessonResponse;
import az.code.lmscodeacademy.entity.lesson.Lesson;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ParticipationResponse {
    private Long id;

//    private LocalDate lessonDate;
    private boolean attendance;
    private double percentage;
    private LessonResponse lesson;
}
