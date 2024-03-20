package az.code.lmscodeacademy.dto.response.assignment;

import az.code.lmscodeacademy.dto.response.group.GroupResponse;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AssignmentResponse {
    private Long id;

    private String title;
    private String description;
    private LocalDate dueDate;
    private String submissionRequirements;
    private GroupResponse group;
}
