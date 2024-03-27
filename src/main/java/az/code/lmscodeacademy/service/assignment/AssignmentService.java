package az.code.lmscodeacademy.service.assignment;

import az.code.lmscodeacademy.dto.request.assignment.AssignmentRequest;
import az.code.lmscodeacademy.dto.response.assignment.AssignmentResponse;
import az.code.lmscodeacademy.entity.assignment.Assignment;
import az.code.lmscodeacademy.entity.group.Group;
import az.code.lmscodeacademy.entity.user.User;
import az.code.lmscodeacademy.exception.handler.ErrorCodes;
import az.code.lmscodeacademy.exception.group.GroupNotFoundException;
import az.code.lmscodeacademy.repository.assignment.AssignmentRepository;
import az.code.lmscodeacademy.repository.group.GroupRepository;
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

    public AssignmentResponse save(AssignmentRequest request, Long groupId) {
        Group group = groupRepository.findById(groupId).
                orElseThrow(() -> new GroupNotFoundException(ErrorCodes.GROUP_NOT_FOUND));

        Assignment assignment = modelMapper.map(request, Assignment.class);
        assignment.setGroup(group);

        return modelMapper.map(assignmentRepository.save(assignment), AssignmentResponse.class);
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
