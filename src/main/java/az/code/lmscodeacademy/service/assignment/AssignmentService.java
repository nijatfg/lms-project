package az.code.lmscodeacademy.service.assignment;

import az.code.lmscodeacademy.dto.request.assignment.AssignmentRequest;
import az.code.lmscodeacademy.dto.response.assignment.AssignmentResponse;
import az.code.lmscodeacademy.entity.assignment.Assignment;
import az.code.lmscodeacademy.entity.enums.UserAuthority;
import az.code.lmscodeacademy.entity.group.Group;
import az.code.lmscodeacademy.entity.user.User;
import az.code.lmscodeacademy.exception.handler.ErrorCodes;
import az.code.lmscodeacademy.exception.group.GroupNotFoundException;
import az.code.lmscodeacademy.repository.assignment.AssignmentRepository;
import az.code.lmscodeacademy.repository.group.GroupRepository;
import az.code.lmscodeacademy.repository.user.UserRepository;
import az.code.lmscodeacademy.service.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final ModelMapper modelMapper;
    private final GroupRepository groupRepository;
    private final EmailService emailService;
    private final UserRepository userRepository;

    public AssignmentResponse save(AssignmentRequest request, Long groupId) {
        Group group = groupRepository.findById(groupId).
                orElseThrow(() -> new GroupNotFoundException(ErrorCodes.GROUP_NOT_FOUND));

        Assignment assignment = modelMapper.map(request, Assignment.class);
        assignment.setGroup(group);

        sendEmailToGroupMembers(assignment);

        return modelMapper.map(assignmentRepository.save(assignment), AssignmentResponse.class);
    }

    public void sendEmailToGroupMembers(Assignment assignment) {
        String subject = "New Assignment Created";
        String content = "Dear Students,\n\nA new assignment has been created for your group.\n\nAssignment Details:\nTitle: "
                + assignment.getTitle() + "\nDescription: " + assignment.getDescription();

        // Log assignment details for debugging
        System.out.println("Assignment Details:");
        System.out.println("Title: " + assignment.getTitle());
        System.out.println("Description: " + assignment.getDescription());

        Group group = assignment.getGroup();

        if (group != null) {
            List<User> students = userRepository.findByGroup(group);

            // Log student details for debugging
            System.out.println("Students in Group:");
            students.forEach(student -> System.out.println("User: " + student.getUsername() + ", Email: " + student.getEmail()));

            List<String> studentEmails = students.stream()
                    .filter(user -> user.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("STUDENT")))
                    .map(User::getEmail)
                    .filter(email -> email != null && !email.isEmpty()) // Filter out null or empty emails
                    .collect(Collectors.toList());


            // Log student emails for debugging
            System.out.println("Student Emails:");
            studentEmails.forEach(System.out::println);

            if (!studentEmails.isEmpty()) {
                emailService.sendEmail(subject, content, studentEmails);
                System.out.println("Email sent successfully.");
            } else {
                System.out.println("No valid student emails found.");
            }
        } else {
            System.out.println("No group found for the assignment.");
        }
    }



    public List<AssignmentResponse> findAll() {
        return assignmentRepository
                .findAll()
                .stream()
                .map(assignment -> modelMapper.map(assignment, AssignmentResponse.class))
                .collect(Collectors.toList());
    }

    public List<AssignmentResponse> findAssignmentsByGroup(Long groupId) {
        List<Assignment> assignments = assignmentRepository.findByGroupId(groupId);

        return assignments.stream()
                .map(assignment -> modelMapper.map(assignment, AssignmentResponse.class))
                .collect(Collectors.toList());
    }

}
