package az.code.lmscodeacademy.dto.response.submission;

import az.code.lmscodeacademy.dto.response.user.UserResponse;
import lombok.Data;

@Data
public class SubmissionResponse {
    private Long id;

    private String content;
    private String link;

    private UserResponse user;
//
//    private AssignmentResponse assignment;
//
//    private GradeResponse grade;
}
