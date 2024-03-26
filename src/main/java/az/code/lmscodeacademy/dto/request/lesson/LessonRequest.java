package az.code.lmscodeacademy.dto.request.lesson;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LessonRequest {
    private String title;
    private String description;
    private LocalDate date;
    private int duration;
}
