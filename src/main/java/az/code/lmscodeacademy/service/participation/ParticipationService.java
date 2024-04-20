package az.code.lmscodeacademy.service.participation;

import az.code.lmscodeacademy.dto.request.participation.ParticipationRequest;
import az.code.lmscodeacademy.dto.response.assignment.AssignmentResponse;
import az.code.lmscodeacademy.dto.response.participation.ParticipationResponse;
import az.code.lmscodeacademy.entity.assignment.Assignment;
import az.code.lmscodeacademy.entity.authority.Authority;
import az.code.lmscodeacademy.entity.enums.Attendance;
import az.code.lmscodeacademy.entity.enums.UserAuthority;
import az.code.lmscodeacademy.entity.group.Group;
import az.code.lmscodeacademy.entity.lesson.Lesson;
import az.code.lmscodeacademy.entity.participation.Participation;
import az.code.lmscodeacademy.entity.user.User;
import az.code.lmscodeacademy.exception.group.GroupNotFoundException;
import az.code.lmscodeacademy.exception.handler.ErrorCodes;
import az.code.lmscodeacademy.exception.lesson.LessonNotFoundException;
import az.code.lmscodeacademy.exception.users.UserNotFoundException;
import az.code.lmscodeacademy.repository.group.GroupRepository;
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
    private final GroupRepository groupRepository;
    private final ModelMapper modelMapper;

    public ParticipationResponse markAttendance(ParticipationRequest request, Long lessonId, Long userId, Long groupId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new LessonNotFoundException(ErrorCodes.LESSON_NOT_FOUND));

        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCodes.USER_NOT_FOUND));

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException(ErrorCodes.GROUP_NOT_FOUND));

//        if (!group.getUsers().contains(currentUser)) {
//            throw new AccessDeniedException("User is not authorized to mark attendance for this group");
//        }

        Participation participation = modelMapper.map(request, Participation.class);
        participation.setLesson(lesson);
        participation.setUser(currentUser);
        participation.setGroup(group);

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

            long attendedLessons = participationRecords.stream()
                    .filter(record -> record.getAttendance() == Attendance.ATTEND)
                    .count();
            long totalLessons = participationRecords.size();

            double participationPercentage = (double) attendedLessons / totalLessons * 100.0;
            return formatPercentage(participationPercentage);
        } else {
            double attendedPercentage = participationRecords.stream()
                    .mapToDouble(record -> {
                        if (record.getAttendance() == Attendance.ATTEND) {
                            return 1.0;
                        } else if (record.getAttendance() == Attendance.LATE) {
                            return 0.5; // Change to appropriate percentage for late attendance
                        } else {
                            return 0.0; // For absent and allowed, no contribution to percentage
                        }
                    })
                    .average()
                    .orElse(0.0);

            double participationPercentage = attendedPercentage * 100.0;
            return formatPercentage(participationPercentage);
        }
    }

    private double formatPercentage(double percentage) {
        return Double.parseDouble(String.format("%.1f", percentage));
    }


    public List<ParticipationResponse> findParticipationRecordsByGroup(Long groupId) {
        List<Participation> participations = participationRepository.findByGroupId(groupId);

        return participations.stream()
                .map(participation -> modelMapper.map(participation, ParticipationResponse.class))
                .collect(Collectors.toList());
    }


}
