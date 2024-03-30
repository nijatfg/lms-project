package az.code.lmscodeacademy.controller.lesson;

import az.code.lmscodeacademy.dto.request.group.GroupRequest;
import az.code.lmscodeacademy.dto.request.lesson.LessonRequest;
import az.code.lmscodeacademy.dto.response.group.GroupResponse;
import az.code.lmscodeacademy.dto.response.lesson.LessonResponse;
import az.code.lmscodeacademy.service.lesson.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lessons")
@RequiredArgsConstructor
@CrossOrigin
public class LessonController {

    private final LessonService lessonService;

    @GetMapping
    public ResponseEntity<List<LessonResponse>> getAllLessons() {
        List<LessonResponse> lessons = lessonService.findAll();
        return ResponseEntity.ok(lessons);
    }

    @PostMapping("/{groupName}")
    public ResponseEntity<LessonResponse> createLesson(
            @RequestBody LessonRequest lessonRequest,
            @PathVariable("groupName") String groupName
    ) {
        LessonResponse lessonResponse = lessonService.save(lessonRequest, groupName);
        return ResponseEntity.status(HttpStatus.CREATED).body(lessonResponse);
    }

    @GetMapping("/findByDate")
    public ResponseEntity<List<LessonResponse>> getLessonsByDate(@RequestParam String date) {
        try {
            List<LessonResponse> lessons = lessonService.findByDate(date);
            return ResponseEntity.ok(lessons);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
