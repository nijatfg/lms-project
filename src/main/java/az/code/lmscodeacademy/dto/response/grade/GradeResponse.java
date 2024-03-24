package az.code.lmscodeacademy.dto.response.grade;

import az.code.lmscodeacademy.dto.response.submission.SubmissionResponse;
import az.code.lmscodeacademy.entity.submission.Submission;
import lombok.Data;

@Data
public class GradeResponse {
    private int score;
    private String feedback;

    private SubmissionResponse submission;
}
