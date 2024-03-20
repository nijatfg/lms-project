package az.code.lmscodeacademy.service.group;

import az.code.lmscodeacademy.dto.request.group.GroupRequest;
import az.code.lmscodeacademy.dto.response.group.GroupResponse;
import az.code.lmscodeacademy.entity.course.Course;
import az.code.lmscodeacademy.entity.group.Group;
import az.code.lmscodeacademy.exception.course.CourseNotFoundException;
import az.code.lmscodeacademy.exception.handler.ErrorCodes;
import az.code.lmscodeacademy.exception.group.GroupNotFoundException;
import az.code.lmscodeacademy.repository.course.CourseRepository;
import az.code.lmscodeacademy.repository.group.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    public List<GroupResponse> findAll() {
        return groupRepository
                .findAll()
                .stream()
                .map(group -> modelMapper.map(group, GroupResponse.class))
                .collect(Collectors.toList());
    }

    public GroupResponse findById(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new CourseNotFoundException(ErrorCodes.GROUP_NOT_FOUND));

        return modelMapper.map(group, GroupResponse.class);
    }

    public GroupResponse save(GroupRequest request, Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(ErrorCodes.COURSE_NOT_FOUND));

        Group group = modelMapper.map(request, Group.class);
        group.setCourse(course);

        return modelMapper.map(groupRepository.save(group), GroupResponse.class);
    }

    public GroupResponse updateGroup(Long groupId, GroupRequest groupRequest) {
        Group existingGroup = groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException(ErrorCodes.GROUP_NOT_FOUND));

        if (groupRequest.getName() != null && !groupRequest.getName().equals(existingGroup.getName())) {
            existingGroup.setName(groupRequest.getName());
        }

        Group updatedGroup = groupRepository.save(existingGroup);
        return modelMapper.map(updatedGroup, GroupResponse.class);
    }

    public void delete(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new CourseNotFoundException(ErrorCodes.GROUP_NOT_FOUND));

        groupRepository.delete(group);
    }
}
