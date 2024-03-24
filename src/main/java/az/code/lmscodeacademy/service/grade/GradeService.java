package az.code.lmscodeacademy.service.grade;

import az.code.lmscodeacademy.dto.request.request.GradeRequest;
import az.code.lmscodeacademy.dto.response.grade.GradeResponse;
import az.code.lmscodeacademy.entity.grade.Grade;
import az.code.lmscodeacademy.entity.submission.Submission;
import az.code.lmscodeacademy.entity.user.User;
import az.code.lmscodeacademy.exception.handler.ErrorCodes;
import az.code.lmscodeacademy.exception.submission.SubmissionNotFoundException;
import az.code.lmscodeacademy.exception.users.UserNotFoundException;
import az.code.lmscodeacademy.repository.grade.GradeRepository;
import az.code.lmscodeacademy.repository.submission.SubmissionRepository;
import az.code.lmscodeacademy.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GradeService {

    private final GradeRepository gradeRepository;
    private final SubmissionRepository submissionRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public GradeResponse gradeSubmission(Long submissionId, GradeRequest gradeRequest) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new SubmissionNotFoundException(ErrorCodes.SUBMISSION_NOT_FOUND));

        Grade grade = new Grade();
        grade.setScore(gradeRequest.getScore());
        grade.setFeedback(gradeRequest.getFeedback());
        grade.setSubmission(submission);

        Grade savedGrade = gradeRepository.save(grade);

        return modelMapper.map(savedGrade, GradeResponse.class);
    }

    public List<GradeResponse> findAllGrades() {
        return gradeRepository
                .findAll()
                .stream()
                .map(grade -> modelMapper.map(grade, GradeResponse.class))
                .collect(Collectors.toList());
    }

    public GradeResponse findGradeBySubmission(Long submissionId) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new SubmissionNotFoundException(ErrorCodes.SUBMISSION_NOT_FOUND));

        Grade grade = gradeRepository.findBySubmission(submission);

        return modelMapper.map(grade, GradeResponse.class);
    }
}
