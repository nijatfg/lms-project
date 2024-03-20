package az.code.lmscodeacademy.service;

import az.code.lmscodeacademy.dto.request.AssignmentRequest;
import az.code.lmscodeacademy.dto.response.AssignmentResponse;
import az.code.lmscodeacademy.entity.Assignment;
import az.code.lmscodeacademy.entity.Group;
import az.code.lmscodeacademy.exception.ErrorCodes;
import az.code.lmscodeacademy.exception.GroupNotFoundException;
import az.code.lmscodeacademy.repository.AssignmentRepository;
import az.code.lmscodeacademy.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
}
