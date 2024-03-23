package az.code.lmscodeacademy.service.user;

import az.code.lmscodeacademy.dto.request.login.LoginRequest;
import az.code.lmscodeacademy.dto.request.signup.SignUpRequest;
import az.code.lmscodeacademy.dto.request.user.UserRequest;
import az.code.lmscodeacademy.dto.response.jwt.Response;
import az.code.lmscodeacademy.dto.response.user.UserResponse;
import az.code.lmscodeacademy.entity.authority.Authority;
import az.code.lmscodeacademy.entity.user.User;
import az.code.lmscodeacademy.entity.enums.UserAuthority;
import az.code.lmscodeacademy.exception.email.EmailExistException;
import az.code.lmscodeacademy.exception.handler.ErrorCodes;
import az.code.lmscodeacademy.exception.password.InvalidPasswordException;
import az.code.lmscodeacademy.exception.users.UserNameExistException;
import az.code.lmscodeacademy.exception.users.UserNotFoundException;
import az.code.lmscodeacademy.repository.authority.AuthorityRepository;
import az.code.lmscodeacademy.repository.user.UserRepository;
import az.code.lmscodeacademy.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final ModelMapper modelMapper;


    public ResponseEntity<Response> registerUser(SignUpRequest signUpRequest) {

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new EmailExistException(ErrorCodes.EMAIL_ALREADY_EXIST);
        }

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new UserNameExistException(ErrorCodes.USERNAME_ALREADY_EXIST);
        }

        Authority userAuthority = authorityRepository.findByAuthority(UserAuthority.ADMIN)
                .orElseGet(() -> {
                    Authority authority = new Authority();
                    authority.setAuthority(UserAuthority.ADMIN);
                    return authorityRepository.save(authority);
                });


        User user = User.builder()
                .authorities(List.of(userAuthority))
                .password(signUpRequest.getPassword())
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .build();

        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        String confirmationToken = getConfirmationToken();

        user.setConfirmationToken(confirmationToken);
        userRepository.save(user);

//        emailService.sendMail(signUpRequest.getEmail(),
//                "Confirmation",
//                "http://localhost:8080/api/auth/confirmation?confirmationToken=" + confirmationToken);

        return ResponseEntity.ok(Response
                .builder()
                .jwt(jwtService.issueToken(user))
                .build());

    }

    public ResponseEntity<Response> loginUser(LoginRequest loginRequest) {

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException(ErrorCodes.USER_NOT_FOUND));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException(ErrorCodes.INVALID_PASSWORD);
        }

        return ResponseEntity.ok(Response
                .builder()
                .jwt(jwtService.issueToken(user))
                .build());
    }

    public UserResponse createUser(UserRequest userRequest) {

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

        User user = User.builder()
                .authorities(List.of(userAuthority))
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .build();

        userRepository.save(user);

        return modelMapper.map(user, UserResponse.class);
    }


    public ResponseEntity<?> confirmation(String confirmationToken) {
        Optional<User> user = userRepository.findByConfirmationToken(confirmationToken);
        if (user.isPresent()) {
            User user1 = user.get();
            userRepository.save(user1);

            return ResponseEntity.ok("User confirmed successfully");
        } else {
            return ResponseEntity.ok("Confirmation token is invalid");
        }
    }

    private String getConfirmationToken() {
        UUID gfg = UUID.randomUUID();
        return gfg.toString();
    }

}
