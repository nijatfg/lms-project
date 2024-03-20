package az.code.lmscodeacademy.service.assignment;

import az.code.lmscodeacademy.dto.request.assignment.AssignmentRequest;
import az.code.lmscodeacademy.dto.response.assignment.AssignmentResponse;
import az.code.lmscodeacademy.entity.assignment.Assignment;
import az.code.lmscodeacademy.entity.group.Group;
import az.code.lmscodeacademy.exception.handler.ErrorCodes;
import az.code.lmscodeacademy.exception.group.GroupNotFoundException;
import az.code.lmscodeacademy.repository.assignment.AssignmentRepository;
import az.code.lmscodeacademy.repository.group.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
