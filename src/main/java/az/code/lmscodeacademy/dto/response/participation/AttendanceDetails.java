package az.code.lmscodeacademy.dto.response.participation;

import az.code.lmscodeacademy.dto.response.lesson.LessonResponse;
import lombok.Data;

@Data
public class AttendanceDetails {
    private boolean attendance;
    private LessonResponse lesson;
}
