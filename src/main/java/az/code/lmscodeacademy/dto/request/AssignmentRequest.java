package az.code.lmscodeacademy.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AssignmentRequest {
    private String title;
    private String description;
    private LocalDate dueDate;
    private String submissionRequirements;
}
