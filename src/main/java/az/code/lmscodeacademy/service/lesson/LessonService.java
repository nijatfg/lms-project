package az.code.lmscodeacademy.service.lesson;

import az.code.lmscodeacademy.dto.request.group.GroupRequest;
import az.code.lmscodeacademy.dto.request.lesson.LessonRequest;
import az.code.lmscodeacademy.dto.response.group.GroupResponse;
import az.code.lmscodeacademy.dto.response.lesson.LessonResponse;
import az.code.lmscodeacademy.entity.group.Group;
import az.code.lmscodeacademy.entity.lesson.Lesson;
import az.code.lmscodeacademy.exception.group.GroupNotFoundException;
import az.code.lmscodeacademy.exception.handler.ErrorCodes;
import az.code.lmscodeacademy.exception.lesson.LessonNotFoundException;
import az.code.lmscodeacademy.repository.group.GroupRepository;
import az.code.lmscodeacademy.repository.lesson.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final GroupRepository groupRepository;
    private final ModelMapper modelMapper;

    public List<LessonResponse> findAll() {
        return lessonRepository
                .findAll()
                .stream()
                .map(lesson -> modelMapper.map(lesson, LessonResponse.class))
                .collect(Collectors.toList());
    }

    public LessonResponse save(LessonRequest request, String groupName) {
        Group group = groupRepository.findByName(groupName)
                .orElseThrow(() -> new GroupNotFoundException(ErrorCodes.GROUP_NOT_FOUND));

        Lesson lesson = modelMapper.map(request, Lesson.class);
        lesson.setGroup(group);

        return modelMapper.map(lessonRepository.save(lesson), LessonResponse.class);
    }

    public List<LessonResponse> findByDate(String date) {
        LocalDate lessonDate = LocalDate.parse(date); // Parse the date string to LocalDate

        List<Lesson> lessons = lessonRepository.findByDate(lessonDate);
        if (lessons.isEmpty()) {
            throw new LessonNotFoundException(ErrorCodes.LESSON_NOT_FOUND);
        }

        return lessons.stream()
                .map(lesson -> modelMapper.map(lesson, LessonResponse.class))
                .collect(Collectors.toList());
    }

    public LessonResponse updateLesson(Long lessonId, LessonRequest lessonRequest) {
        Lesson existingLesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new LessonNotFoundException(ErrorCodes.LESSON_NOT_FOUND));

        if (lessonRequest.getTitle() != null && !lessonRequest.getTitle().equals(existingLesson.getTitle())) {
            existingLesson.setTitle(lessonRequest.getTitle());
        }

        if (lessonRequest.getDescription() != null && !lessonRequest.getDescription().equals(existingLesson.getDescription())) {
            existingLesson.setDescription(lessonRequest.getDescription());
        }

        if (lessonRequest.getDate() != null && !lessonRequest.getDate().equals(existingLesson.getDate())) {
            existingLesson.setDate(lessonRequest.getDate());
        }

        Lesson updatedLesson = lessonRepository.save(existingLesson);
        return modelMapper.map(updatedLesson, LessonResponse.class);
    }

    public void delete(Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new LessonNotFoundException(ErrorCodes.LESSON_NOT_FOUND));

        lessonRepository.delete(lesson);
    }

}
