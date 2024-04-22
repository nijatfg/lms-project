package az.code.lmscodeacademy.service.group;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import az.code.lmscodeacademy.dto.request.group.GroupRequest;
import az.code.lmscodeacademy.dto.response.group.GroupResponse;
import az.code.lmscodeacademy.entity.course.Course;
import az.code.lmscodeacademy.entity.group.Group;
import az.code.lmscodeacademy.repository.course.CourseRepository;
import az.code.lmscodeacademy.repository.group.GroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

class GroupServiceTest {

    @InjectMocks
    private GroupService groupService;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        // Given
        Group group1 = new Group();
        group1.setId(1L);
        group1.setName("Group 1");

        Group group2 = new Group();
        group2.setId(2L);
        group2.setName("Group 2");

        when(groupRepository.findAll()).thenReturn(Arrays.asList(group1, group2));

        GroupResponse groupResponse1 = new GroupResponse();
        groupResponse1.setId(1L);
        groupResponse1.setName("Group 1");

        GroupResponse groupResponse2 = new GroupResponse();
        groupResponse2.setId(2L);
        groupResponse2.setName("Group 2");

        when(modelMapper.map(group1, GroupResponse.class)).thenReturn(groupResponse1);
        when(modelMapper.map(group2, GroupResponse.class)).thenReturn(groupResponse2);

        // When
        List<GroupResponse> groupResponses = groupService.findAll();

        // Then
        assertEquals(2, groupResponses.size());
        assertEquals("Group 1", groupResponses.get(0).getName());
        assertEquals("Group 2", groupResponses.get(1).getName());
    }

    @Test
    void testFindById() {
        // Given
        Long groupId = 1L;
        Group group = new Group();
        group.setId(groupId);
        group.setName("Group 1");

        when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));

        GroupResponse expectedResponse = new GroupResponse();
        expectedResponse.setId(groupId);
        expectedResponse.setName("Group 1");

        when(modelMapper.map(group, GroupResponse.class)).thenReturn(expectedResponse);

        // When
        GroupResponse response = groupService.findById(groupId);

        // Then
        assertNotNull(response);
        assertEquals(groupId, response.getId());
        assertEquals("Group 1", response.getName());
    }

    @Test
    void testSave() {
        //Arrange
        GroupRequest groupRequest = new GroupRequest();
        String courseName = "Java";
        Course course = new Course();
        GroupResponse groupResponse = new GroupResponse();
        Group group = new Group();

        when(courseRepository.findByName(courseName)).thenReturn(Optional.of(course));
        when(modelMapper.map(groupRequest,Group.class)).thenReturn(group);
        when(groupRepository.save(group)).thenReturn(group);
        when(modelMapper.map(group,GroupResponse.class)).thenReturn(groupResponse);

        //Act
        GroupResponse actualResponse = groupService.save(groupRequest,courseName);

        //Assert
        assertEquals(groupResponse,actualResponse);
        assertEquals(course,group.getCourse());
    }

    @Test
    void testUpdateGroup() {
        // Given
        Long groupId = 1L;
        GroupRequest request = new GroupRequest();
        request.setName("Updated Group");

        Group existingGroup = new Group();
        existingGroup.setId(groupId);
        existingGroup.setName("Group 1");

        when(groupRepository.findById(groupId)).thenReturn(Optional.of(existingGroup));

        Group updatedGroup = new Group();
        updatedGroup.setId(groupId);
        updatedGroup.setName(request.getName());

        when(groupRepository.save(any(Group.class))).thenReturn(updatedGroup);

        GroupResponse expectedResponse = new GroupResponse();
        expectedResponse.setId(updatedGroup.getId());
        expectedResponse.setName(updatedGroup.getName());

        when(modelMapper.map(updatedGroup, GroupResponse.class)).thenReturn(expectedResponse);

        // When
        GroupResponse response = groupService.updateGroup(groupId, request);

        // Then
        assertNotNull(response);
        assertEquals(updatedGroup.getId(), response.getId());
        assertEquals(updatedGroup.getName(), response.getName());
    }

    @Test
    void testDelete() {
        // Given
        Long groupId = 1L;
        Group group = new Group();
        group.setId(groupId);
        group.setName("Group 1");

        when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));

        // When
        groupService.delete(groupId);

        // Then
        verify(groupRepository, times(1)).delete(group);
    }
}
