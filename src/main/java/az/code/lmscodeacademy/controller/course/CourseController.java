package az.code.lmscodeacademy.controller.course;

import az.code.lmscodeacademy.dto.request.course.CourseRequest;
import az.code.lmscodeacademy.dto.response.course.CourseResponse;
import az.code.lmscodeacademy.service.course.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
@CrossOrigin
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<List<CourseResponse>> getAllCourses() {
        List<CourseResponse> courses = courseService.findAll();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponse> getCourseById(@PathVariable("id") Long courseId) {
        CourseResponse course = courseService.findById(courseId);
        return ResponseEntity.ok(course);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CourseResponse> createCourse(@RequestBody CourseRequest courseRequest) {
        CourseResponse createdCourse = courseService.save(courseRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseResponse> updateCourse(
            @PathVariable("id") Long courseId,
            @RequestBody CourseRequest courseRequest
    ) {
        CourseResponse updatedCourse = courseService.updateCourse(courseId, courseRequest);
        return ResponseEntity.ok(updatedCourse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable("id") Long courseId) {
        courseService.delete(courseId);
        return ResponseEntity.noContent().build();
    }
}
