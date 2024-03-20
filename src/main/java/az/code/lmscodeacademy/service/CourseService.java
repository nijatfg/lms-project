package az.code.lmscodeacademy.service;

import az.code.lmscodeacademy.dto.request.CourseRequest;
import az.code.lmscodeacademy.dto.response.CourseResponse;
import az.code.lmscodeacademy.entity.Course;
import az.code.lmscodeacademy.exception.CourseNotFoundException;
import az.code.lmscodeacademy.exception.ErrorCodes;
import az.code.lmscodeacademy.repository.CourseRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    public List<CourseResponse> findAll() {
        return courseRepository
                .findAll()
                .stream()
                .map(course -> modelMapper.map(course, CourseResponse.class))
                .collect(Collectors.toList());
    }

    public CourseResponse findById(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(ErrorCodes.COURSE_NOT_FOUND));

        return modelMapper.map(course, CourseResponse.class);
    }

    public CourseResponse save(CourseRequest request) {
        Course course = modelMapper.map(request, Course.class);

        return modelMapper.map(courseRepository.save(course), CourseResponse.class);
    }

    public CourseResponse updateCourse(Long courseId, CourseRequest courseRequest) {
        Course existingCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(ErrorCodes.COURSE_NOT_FOUND));

        if (courseRequest.getName() != null && !courseRequest.getName().equals(existingCourse.getName())) {
            existingCourse.setName(courseRequest.getName());
        }

        if (courseRequest.getDescription() != null && !courseRequest.getDescription().equals(existingCourse.getDescription())) {
            existingCourse.setDescription(courseRequest.getDescription());
        }

        Course updatedCourse = courseRepository.save(existingCourse);
        return modelMapper.map(updatedCourse, CourseResponse.class);
    }

    public void delete(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(ErrorCodes.COURSE_NOT_FOUND));

        courseRepository.delete(course);
    }
}
