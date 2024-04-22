package az.code.lmscodeacademy.service.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import az.code.lmscodeacademy.dto.request.user.UserRequest;
import az.code.lmscodeacademy.dto.response.group.GroupResponse;
import az.code.lmscodeacademy.dto.response.user.UserResponse;
import az.code.lmscodeacademy.entity.authority.Authority;
import az.code.lmscodeacademy.entity.enums.MessageStatus;
import az.code.lmscodeacademy.entity.enums.UserAuthority;
import az.code.lmscodeacademy.entity.group.Group;
import az.code.lmscodeacademy.entity.user.User;
import az.code.lmscodeacademy.repository.authority.AuthorityRepository;
import az.code.lmscodeacademy.repository.group.GroupRepository;
import az.code.lmscodeacademy.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthorityRepository authorityRepository;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser() {
        // Mock Group
        Group group = new Group();
        group.setName("Java");
        Authority authority = new Authority();
        authority.setAuthority(UserAuthority.ADMIN);

        // Mock User and UserRequest
        User mockUser = User.builder()
                .firstName("Nicat")
                .lastName("Quliyev")
                .username("Nici")
                .email("quliyevv.nicat2003@gmail.com")
                .group(group)
                .authorities(List.of(authority))
                .build();

        UserRequest mockUserRequest = UserRequest.builder()
                .firstName("Nicat")
                .lastName("Quliyev")
                .username("Nici")
                .email("quliyevv.nicat2003@gmail.com")
                .authority(UserAuthority.ADMIN)
                .build();

        // Mock UserRepository save behavior
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        // Mock ModelMapper behavior for mapping UserRequest to User and User to UserResponse
        when(modelMapper.map(mockUserRequest, User.class)).thenReturn(mockUser);
        when(modelMapper.map(mockUser, UserResponse.class)).thenReturn(UserResponse.builder()
                .firstName("Nicat")
                .lastName("Quliyev")
                .username("Nici")
                .email("quliyevv.nicat2003@gmail.com")
                .group(new GroupResponse()) // Mock empty GroupResponse for simplicity
                .authorities(List.of(authority))
                .build());

        // Mock GroupRepository behavior for finding group by name
        when(groupRepository.findByName("Java")).thenReturn(Optional.of(group));

        // Call the createUser method
        UserResponse userResponse = userService.createUser(mockUserRequest, "Java");

        // Verify the response
        assertNotNull(userResponse);
        assertEquals("Nicat", userResponse.getFirstName());
        assertEquals("Quliyev", userResponse.getLastName());
        assertEquals("Nici", userResponse.getUsername());
        assertEquals("quliyevv.nicat2003@gmail.com", userResponse.getEmail());
    }




    @Test
    void testFindAllUsers() {
        // Given
        List<User> userList = new ArrayList<>();
        userList.add(new User());
        userList.add(new User());

        when(userRepository.findAll()).thenReturn(userList);

        List<UserResponse> expectedResponses = new ArrayList<>();
        expectedResponses.add(new UserResponse());
        expectedResponses.add(new UserResponse());

        when(modelMapper.map(any(User.class), eq(UserResponse.class)))
                .thenReturn(new UserResponse())
                .thenReturn(new UserResponse());

        // When
        List<UserResponse> responses = userService.findAllUsers();

        // Then
        assertNotNull(responses);
        assertEquals(2, responses.size());
    }

    @Test
    void testFindById() {
        // Given
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setUsername("johndoe");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserResponse expectedResponse = new UserResponse();
        expectedResponse.setId(userId);
        expectedResponse.setUsername("johndoe");

        when(modelMapper.map(user, UserResponse.class)).thenReturn(expectedResponse);

        // When
        UserResponse response = userService.findById(userId);

        // Then
        assertNotNull(response);
        assertEquals(userId, response.getId());
        assertEquals("johndoe", response.getUsername());
    }

    @Test
    void testFindByGroupId() {
        // Given
        Long groupId = 1L;
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());

        when(userRepository.findByGroupId(groupId)).thenReturn(users);

        when(modelMapper.map(any(User.class), eq(UserResponse.class)))
                .thenReturn(new UserResponse())
                .thenReturn(new UserResponse());

        // When
        List<UserResponse> responses = userService.findByGroupId(groupId);

        // Then
        assertNotNull(responses);
        assertEquals(2, responses.size());
    }

    @Test
    void testFindByUsername() {
        // Given
        String username = "johndoe";
        User user = new User();
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        UserResponse expectedResponse = new UserResponse();
        expectedResponse.setUsername(username);

        when(modelMapper.map(user, UserResponse.class)).thenReturn(expectedResponse);

        // When
        UserResponse response = userService.findByUsername(username);

        // Then
        assertNotNull(response);
        assertEquals(username, response.getUsername());
    }

    @Test
    void testUpdateUser() {
        // Given
        Long userId = 1L;
        UserRequest userRequest = new UserRequest();
        userRequest.setFirstName("Nicat");
        userRequest.setLastName("Quliyev");
        userRequest.setUsername("nici");
        userRequest.setEmail("quliyevv.nicat2003@gmail.com");
        userRequest.setPassword("password");

        Group group = new Group();
        group.setId(1L);
        group.setName("Test Group");

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setUsername("nici");
        existingUser.setEmail("quliyevv.nicat2003@gmail.com");
        existingUser.setGroup(group);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(groupRepository.findByName("Test Group")).thenReturn(Optional.of(group));
        when(passwordEncoder.encode("password")).thenReturn("$2a$12$.8.Geyw.LGq2G.LQLSGKo.mVXaMaUiGHqHrzYCRC/scaSSfQSTlu2\n");

        // When
        UserResponse response = userService.updateUser(userId, userRequest, "Test Group");

        // Then
        assertEquals(userId, response.getId());
        assertEquals("Nicat", response.getFirstName());
        assertEquals("Quliyev", response.getLastName());
        assertEquals("nici", response.getUsername());
        assertEquals("quliyevv.nicat2003@gmail.com", response.getEmail());

        // Verify repository interactions
        verify(userRepository, times(1)).findById(userId);
        verify(groupRepository, times(1)).findByName("Test Group");
        verify(passwordEncoder, times(1)).encode("password");
        verify(userRepository, times(1)).save(existingUser);
    }


    @Test
    void testDeleteUser() {
        // Given
        Long userId = 1L;

        // When
        userService.deleteUser(userId);

        // Then
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testDisconnect() {
        // Given
        User user = new User();
        user.setUsername("johndoe");

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        // When
        userService.disconnect(user);

        // Then
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testFindConnectedUsers() {
        // Given
        List<User> connectedUsers = new ArrayList<>();
        connectedUsers.add(new User());
        connectedUsers.add(new User());

        when(userRepository.findAllByStatus(MessageStatus.ONLINE)).thenReturn(connectedUsers);

        when(modelMapper.map(any(User.class), eq(UserResponse.class)))
                .thenReturn(new UserResponse())
                .thenReturn(new UserResponse());

        // When
        List<User> users = userService.findConnectedUsers();

        // Then
        assertNotNull(users);
        assertEquals(2, users.size());
    }

    // Add tests for other methods as needed
}
