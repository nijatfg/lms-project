package az.code.lmscodeacademy.controller.submission;

import az.code.lmscodeacademy.dto.request.sumbission.SubmissionRequest;
import az.code.lmscodeacademy.dto.response.submission.SubmissionResponse;
import az.code.lmscodeacademy.service.submission.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/submissions")
@RequiredArgsConstructor
@CrossOrigin
public class SubmissionController {

    private final SubmissionService submissionService;

    @PostMapping("/submit/{assignmentId}/{userId}/{groupId}")
    public ResponseEntity<SubmissionResponse> submitAssignment(
            @ModelAttribute SubmissionRequest request,
            @PathVariable Long assignmentId,
            @PathVariable Long userId,
            @PathVariable Long groupId,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) throws IOException {
        if (file != null) {
            return new ResponseEntity<>(submissionService.submitAssignmentWithFile(request, assignmentId, userId, file, groupId), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(submissionService.submitAssignmentWithoutFile(assignmentId, userId, request, groupId), HttpStatus.CREATED);
        }
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {
        byte[] data = submissionService.downloadFile(fileName);

        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity.
                ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }


    @GetMapping("/assignment/{assignmentId}/groups/{groupId}")
    public ResponseEntity<List<SubmissionResponse>> getAllSubmissionsByAssignmentIdAndGroupId(@PathVariable Long assignmentId,
                                                                                              @PathVariable Long groupId) {
        return new ResponseEntity<>(submissionService.getAllSubmissionsByAssignmentIdAndGroupId(assignmentId, groupId), HttpStatus.OK);
    }

    @GetMapping("/assignment/{assignmentId}/groups/{groupId}/users/{userId}")
    public ResponseEntity<List<SubmissionResponse>> getAllSubmissionsByAssignmentIdGroupIdUserId(@PathVariable Long assignmentId,
                                                                                                 @PathVariable Long groupId,
                                                                                                 @PathVariable Long userId) {
        return new ResponseEntity<>(submissionService.getAllSubmissionsByAssignmentIdGroupIdAndUserId(assignmentId, groupId, userId), HttpStatus.OK);
    }
}
