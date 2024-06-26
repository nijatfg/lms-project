package az.code.lmscodeacademy.dto.response.lesson;

import az.code.lmscodeacademy.dto.response.group.GroupResponse;
import az.code.lmscodeacademy.entity.group.Group;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LessonResponse {
    private Long id;
    private LocalDate date;
    private GroupResponse group;
    private String description;
}
