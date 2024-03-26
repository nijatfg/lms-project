package az.code.lmscodeacademy.service.participation;

import az.code.lmscodeacademy.dto.request.participation.ParticipationRequest;
import az.code.lmscodeacademy.dto.response.participation.ParticipationResponse;
import az.code.lmscodeacademy.entity.authority.Authority;
import az.code.lmscodeacademy.entity.enums.UserAuthority;
import az.code.lmscodeacademy.entity.lesson.Lesson;
import az.code.lmscodeacademy.entity.participation.Participation;
import az.code.lmscodeacademy.entity.user.User;
import az.code.lmscodeacademy.exception.handler.ErrorCodes;
import az.code.lmscodeacademy.exception.lesson.LessonNotFoundException;
import az.code.lmscodeacademy.exception.users.UserNotFoundException;
import az.code.lmscodeacademy.repository.lesson.LessonRepository;
import az.code.lmscodeacademy.repository.participation.ParticipationRepository;
import az.code.lmscodeacademy.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParticipationService {

    private final ParticipationRepository participationRepository;
    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public ParticipationResponse markAttendance(ParticipationRequest request, Long lessonId, Long userId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new LessonNotFoundException(ErrorCodes.LESSON_NOT_FOUND));

        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCodes.USER_NOT_FOUND));

        if (currentUser.getAuthorities().contains(UserAuthority.STUDENT)) {
            throw new AccessDeniedException("Students are not authorized to mark attendance");
        }

        Participation participation = modelMapper.map(request, Participation.class);
        participation.setLesson(lesson);
        participation.setUser(currentUser);

        Participation savedParticipation = participationRepository.save(participation);

        return modelMapper.map(savedParticipation, ParticipationResponse.class);
    }

    public List<ParticipationResponse> getParticipationRecords(Long userId) {
        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCodes.USER_NOT_FOUND));

        List<Participation> participationList;

        participationList = participationRepository.findByUser(currentUser);
//        if (currentUser.getAuthorities().contains(UserAuthority.STUDENT)) {
//
//            participationList = participationRepository.findByUser(currentUser);
//        } else {
//            participationList = participationRepository.findAll();
//        }

        return participationList.stream()
                .map(participation -> modelMapper.map(participation, ParticipationResponse.class))
                .collect(Collectors.toList());
    }

    public double calculateParticipationPercentage(Long userId) {
        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCodes.USER_NOT_FOUND));

        List<ParticipationResponse> participationRecords = getParticipationRecords(userId);

        if (currentUser.getAuthorities().contains(UserAuthority.STUDENT)) {
            if (participationRecords.isEmpty()) {
                return 0.0;
            }

            long attendedLessons = participationRecords.stream().filter(ParticipationResponse::isAttendance).count();
            long totalLessons = participationRecords.size();

            return (double) attendedLessons / totalLessons * 100.0;
        } else {
            double attendedPercentage = participationRecords.stream()
                    .mapToDouble(record -> record.isAttendance() ? 1.0 : 0.0)
                    .average()
                    .orElse(0.0);

            return attendedPercentage * 100.0;
        }
    }


}
