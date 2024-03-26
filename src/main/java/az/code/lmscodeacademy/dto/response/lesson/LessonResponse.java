package az.code.lmscodeacademy.dto.response.lesson;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LessonResponse {
    private Long id;
    private LocalDate date;
}
