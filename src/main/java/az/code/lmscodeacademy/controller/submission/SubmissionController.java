package az.code.lmscodeacademy.controller.submission;

import az.code.lmscodeacademy.dto.request.sumbission.SubmissionRequest;
import az.code.lmscodeacademy.dto.response.submission.SubmissionResponse;
import az.code.lmscodeacademy.service.submission.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/submissions")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;

    @PostMapping("/submit/{assignmentId}/{userId}")
    public ResponseEntity<SubmissionResponse> submitAssignment(
            @ModelAttribute SubmissionRequest request,
            @PathVariable Long assignmentId,
            @PathVariable Long userId,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) throws IOException {
        if (file != null) {
            return new ResponseEntity<>(submissionService.submitAssignmentWithFile(request, assignmentId, userId, file), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(submissionService.submitAssignmentWithoutFile(assignmentId, userId, request), HttpStatus.CREATED);
        }
    }


    @GetMapping("/assignment/{assignmentId}/submissions")
    public ResponseEntity<List<SubmissionResponse>> getAllSubmissionsByAssignmentId(@PathVariable Long assignmentId) {
        return new ResponseEntity<>(submissionService.getAllSubmissionsByAssignmentId(assignmentId), HttpStatus.OK);
    }
}
