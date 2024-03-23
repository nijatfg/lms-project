package az.code.lmscodeacademy.dto.response.submission;

import lombok.Data;

@Data
public class SubmissionResponse {
    private Long id;

    private String content;
    private String link;

//    private User user;
//
//    private AssignmentResponse assignment;
//
//    private GradeResponse grade;
}
