package az.code.lmscodeacademy.service.assignment;

import az.code.lmscodeacademy.dto.request.assignment.AssignmentRequest;
import az.code.lmscodeacademy.dto.response.assignment.AssignmentResponse;
import az.code.lmscodeacademy.entity.assignment.Assignment;
import az.code.lmscodeacademy.entity.group.Group;
import az.code.lmscodeacademy.exception.assignment.AssignmentNotFoundException;
import az.code.lmscodeacademy.exception.group.GroupNotFoundException;
import az.code.lmscodeacademy.exception.handler.ErrorCodes;
import az.code.lmscodeacademy.repository.assignment.AssignmentRepository;
import az.code.lmscodeacademy.repository.group.GroupRepository;
import az.code.lmscodeacademy.repository.user.UserRepository;
import az.code.lmscodeacademy.service.email.EmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AssignmentServiceTest {

    @Mock
    private AssignmentRepository assignmentRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AssignmentService assignmentService;

    @Test
    void testSave() {
        // Given
        AssignmentRequest request = new AssignmentRequest();
        request.setTitle("Test Assignment");
        request.setDescription("Description of test assignment");
        request.setDueDate(LocalDate.now());
        request.setSubmissionRequirements("Submission requirements for test assignment");

        Group group = new Group();
        group.setId(1L);
        group.setName("Test Group");

        Assignment assignment = new Assignment();
        assignment.setId(1L);
        assignment.setTitle("Test Assignment");
        assignment.setDescription("Description of test assignment");
        assignment.setDueDate(LocalDate.now());
        assignment.setSubmissionRequirements("Submission requirements for test assignment");
        assignment.setGroup(group);

        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
        when(modelMapper.map(request, Assignment.class)).thenReturn(assignment);
        when(assignmentRepository.save(any())).thenReturn(assignment);

        // Assuming your modelMapper.map() returns the same instance for AssignmentResponse for simplicity
        AssignmentResponse expectedResponse = modelMapper.map(assignment, AssignmentResponse.class);

        // When
        AssignmentResponse response = assignmentService.save(request, 1L);

        // Then
        assertNotNull(response);
        assertEquals(expectedResponse.getDescription(), response.getDescription());
        verify(emailService, times(1)).sendEmail(any(), any(), any());
    }


    @Test
    void testSave_GroupNotFound() {
        // Given
        AssignmentRequest request = new AssignmentRequest();
        request.setTitle("Test Assignment");
        request.setDescription("Description of test assignment");
        request.setDueDate(LocalDate.now());
        request.setSubmissionRequirements("Submission requirements for test assignment");

        when(groupRepository.findById(1L)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(GroupNotFoundException.class, () -> assignmentService.save(request, 1L));
    }

    @Test
    void testFindAll() {
        // Given
        Assignment assignment = new Assignment();
        assignment.setId(1L);
        assignment.setTitle("Test Assignment");
        assignment.setDescription("Description of test assignment");
        assignment.setDueDate(LocalDate.now());
        assignment.setSubmissionRequirements("Submission requirements for test assignment");

        when(assignmentRepository.findAll()).thenReturn(Collections.singletonList(assignment));
        when(modelMapper.map(any(), eq(AssignmentResponse.class))).thenReturn(new AssignmentResponse());

        // When
        List<AssignmentResponse> responses = assignmentService.findAll();

        // Then
        assertNotNull(responses);
        assertFalse(responses.isEmpty());
        assertEquals(1, responses.size());
    }

    @Test
    void testFindAssignmentsByGroup() {
        // Given
        Group group = new Group();
        group.setId(1L);

        Assignment assignment = new Assignment();
        assignment.setId(1L);
        assignment.setTitle("Test Assignment");
        assignment.setDescription("Description of test assignment");
        assignment.setDueDate(LocalDate.now());
        assignment.setSubmissionRequirements("Submission requirements for test assignment");
        assignment.setGroup(group);

        when(assignmentRepository.findByGroupId(1L)).thenReturn(Collections.singletonList(assignment));
        when(modelMapper.map(any(), eq(AssignmentResponse.class))).thenReturn(new AssignmentResponse());

        // When
        List<AssignmentResponse> responses = assignmentService.findAssignmentsByGroup(1L);

        // Then
        assertNotNull(responses);
        assertFalse(responses.isEmpty());
        assertEquals(1, responses.size());
    }

    @Test
    void testFindById() {
        // Given
        Assignment assignment = new Assignment();
        assignment.setId(1L);
        assignment.setTitle("Test Assignment");
        assignment.setDescription("Description of test assignment");
        assignment.setDueDate(LocalDate.now());
        assignment.setSubmissionRequirements("Submission requirements for test assignment");

        when(assignmentRepository.findById(1L)).thenReturn(Optional.of(assignment));
        when(modelMapper.map(any(), eq(AssignmentResponse.class))).thenReturn(new AssignmentResponse());

        // When
        AssignmentResponse response = assignmentService.findById(1L);

        // Then
        assertNotNull(response);
        assertEquals("Description of test assignment", response.getDescription());
    }

    @Test
    void testFindById_AssignmentNotFound() {
        // Given
        when(assignmentRepository.findById(1L)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(AssignmentNotFoundException.class, () -> assignmentService.findById(1L));
    }


}
