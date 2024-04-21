package az.code.lmscodeacademy.controller.assignment;

import az.code.lmscodeacademy.dto.request.assignment.AssignmentRequest;
import az.code.lmscodeacademy.dto.response.assignment.AssignmentResponse;
import az.code.lmscodeacademy.service.assignment.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/assignments")
@RequiredArgsConstructor
@CrossOrigin
public class AssignmentController {

    private final AssignmentService assignmentService;

    @PostMapping("/groups/{groupId}")
    @PreAuthorize("hasAuthority('TEACHER')")
    public ResponseEntity<AssignmentResponse> createAssignment(
            @RequestBody AssignmentRequest request,
            @PathVariable("groupId") Long groupId) {
        return new ResponseEntity<>(assignmentService.save(request, groupId), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AssignmentResponse>> findAll() {
        return new ResponseEntity<>(assignmentService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{assignmentId}")
    public ResponseEntity<AssignmentResponse> findById(@PathVariable Long assignmentId) {
        return new ResponseEntity<>(assignmentService.findById(assignmentId), HttpStatus.OK);
    }


    @GetMapping("/groups/{groupId}")
    public ResponseEntity<List<AssignmentResponse>> findAssignmentsByGroup(@PathVariable Long groupId) {
        return new ResponseEntity<>(assignmentService.findAssignmentsByGroup(groupId), HttpStatus.OK);
    }
}
