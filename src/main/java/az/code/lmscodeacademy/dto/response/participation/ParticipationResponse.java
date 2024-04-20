package az.code.lmscodeacademy.dto.response.participation;

import az.code.lmscodeacademy.dto.response.lesson.LessonResponse;
import az.code.lmscodeacademy.dto.response.user.UserResponse;
import az.code.lmscodeacademy.entity.enums.Attendance;
import az.code.lmscodeacademy.entity.lesson.Lesson;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ParticipationResponse {
    private Long id;

//    private LocalDate lessonDate;
    private Attendance attendance;
    private double percentage;
    private LessonResponse lesson;
    private UserResponse user;
}
