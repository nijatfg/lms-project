package az.code.lmscodeacademy.controller.grade;

import az.code.lmscodeacademy.dto.request.request.GradeRequest;
import az.code.lmscodeacademy.dto.response.grade.GradeResponse;
import az.code.lmscodeacademy.service.grade.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/grades")
@RequiredArgsConstructor
public class GradeController {

    private final GradeService gradeService;

    @PostMapping("/submissions/{submissionId}")
    public ResponseEntity<GradeResponse> markGrade(@PathVariable Long submissionId, @RequestBody GradeRequest request) {
        return new ResponseEntity<>(gradeService.gradeSubmission(submissionId, request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<GradeResponse>> findAllGrades() {
        return new ResponseEntity<>(gradeService.findAllGrades(), HttpStatus.OK);
    }

    @GetMapping("/submissions/{submissionId}")
    public ResponseEntity<GradeResponse> findGradeBySubmission(@PathVariable Long submissionId) {
        return new ResponseEntity<>(gradeService.findGradeBySubmission(submissionId), HttpStatus.OK);
    }


}
