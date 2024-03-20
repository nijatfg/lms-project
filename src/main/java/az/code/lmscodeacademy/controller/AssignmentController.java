package az.code.lmscodeacademy.controller;

import az.code.lmscodeacademy.dto.request.AssignmentRequest;
import az.code.lmscodeacademy.dto.response.AssignmentResponse;
import az.code.lmscodeacademy.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/assignments")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;

    @PostMapping("/groups/{groupId}")
    public ResponseEntity<AssignmentResponse> createAssignment(
            @RequestBody AssignmentRequest request,
            @PathVariable("groupId") Long groupId) {
        return new ResponseEntity<>(assignmentService.save(request, groupId), HttpStatus.CREATED);
    }

}
