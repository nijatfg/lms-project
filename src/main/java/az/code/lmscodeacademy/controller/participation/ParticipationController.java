package az.code.lmscodeacademy.controller.participation;

import az.code.lmscodeacademy.dto.request.participation.ParticipationRequest;
import az.code.lmscodeacademy.dto.response.participation.ParticipationResponse;
import az.code.lmscodeacademy.exception.lesson.LessonNotFoundException;
import az.code.lmscodeacademy.exception.users.UserNotFoundException;
import az.code.lmscodeacademy.service.participation.ParticipationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/participation")
@RequiredArgsConstructor
public class ParticipationController {

    private final ParticipationService participationService;

    @PostMapping("/markAttendance/{lessonId}/{userId}")
    public ResponseEntity<ParticipationResponse> markAttendance(@RequestBody ParticipationRequest request,
                                                                @PathVariable Long lessonId,
                                                                @PathVariable Long userId) {
        return new ResponseEntity<>(participationService.markAttendance(request, lessonId, userId), HttpStatus.CREATED);
    }

    @GetMapping("/participationRecords/{userId}")
    public ResponseEntity<List<ParticipationResponse>> getParticipationRecords(@PathVariable Long userId) {
        return new ResponseEntity<>(participationService.getParticipationRecords(userId), HttpStatus.OK);
    }

    @GetMapping("/calculatePercentage/{userId}")
    public ResponseEntity<Double> calculateParticipationPercentage(@PathVariable Long userId) {
        return new ResponseEntity<>(participationService.calculateParticipationPercentage(userId),HttpStatus.OK);
    }
}
