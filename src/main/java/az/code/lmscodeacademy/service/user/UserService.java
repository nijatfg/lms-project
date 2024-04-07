package az.code.lmscodeacademy.service.user;

import az.code.lmscodeacademy.dto.request.user.UserRequest;
import az.code.lmscodeacademy.dto.response.group.GroupResponse;
import az.code.lmscodeacademy.dto.response.user.UserResponse;
import az.code.lmscodeacademy.entity.authority.Authority;
import az.code.lmscodeacademy.entity.enums.MessageStatus;
import az.code.lmscodeacademy.entity.group.Group;
import az.code.lmscodeacademy.entity.user.User;
import az.code.lmscodeacademy.exception.course.CourseNotFoundException;
import az.code.lmscodeacademy.exception.email.EmailExistException;
import az.code.lmscodeacademy.exception.group.GroupNotFoundException;
import az.code.lmscodeacademy.exception.handler.ErrorCodes;
import az.code.lmscodeacademy.exception.users.UserNameExistException;
import az.code.lmscodeacademy.exception.users.UserNotFoundException;
import az.code.lmscodeacademy.repository.authority.AuthorityRepository;
import az.code.lmscodeacademy.repository.group.GroupRepository;
import az.code.lmscodeacademy.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final GroupRepository groupRepository;

    public UserResponse createUser(UserRequest userRequest, String groupName) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new EmailExistException(ErrorCodes.EMAIL_ALREADY_EXIST);
        }

        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new UserNameExistException(ErrorCodes.USERNAME_ALREADY_EXIST);
        }

        Authority userAuthority = authorityRepository.findByAuthority(userRequest.getAuthority())
                .orElseGet(() -> {
                    Authority newAuthority = new Authority();
                    newAuthority.setAuthority(userRequest.getAuthority());
                    return authorityRepository.save(newAuthority);
                });

        Group group = groupRepository.findByName(groupName)
                .orElseThrow(() -> new GroupNotFoundException(ErrorCodes.GROUP_NOT_FOUND));

        User user = User.builder()
                .authorities(List.of(userAuthority))
                .group(group)
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .build();

        userRepository.save(user);

        return modelMapper.map(user, UserResponse.class);
    }


    public List<UserResponse> findAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .collect(Collectors.toList());
    }

    public UserResponse findById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCodes.USER_NOT_FOUND));

        return modelMapper.map(user, UserResponse.class);
    }

    public List<UserResponse> findByGroupId(Long groupId) {
        List<User> users = userRepository.findByGroupId(groupId);

        return users.stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .collect(Collectors.toList());
    }

    public void disconnect(User user) {
        var storedUser = userRepository.findByUsername(user.getUsername()).orElse(null);
        if (storedUser != null) {
            storedUser.setStatus(MessageStatus.OFFLINE);
            userRepository.save(storedUser);
        }

    }

    public List<User> findConnectedUsers() {
        return userRepository.findAllByStatus(MessageStatus.ONLINE);
    }
}
