package az.code.lmscodeacademy.service.auth;

import az.code.lmscodeacademy.dto.request.login.LoginRequest;
import az.code.lmscodeacademy.dto.request.password.ChangePasswordRequest;
import az.code.lmscodeacademy.dto.request.signup.SignUpRequest;
import az.code.lmscodeacademy.dto.response.jwt.Response;
import az.code.lmscodeacademy.dto.response.user.UserResponse;
import az.code.lmscodeacademy.entity.authority.Authority;
import az.code.lmscodeacademy.entity.enums.UserAuthority;
import az.code.lmscodeacademy.entity.user.User;
import az.code.lmscodeacademy.exception.handler.ErrorCodes;
import az.code.lmscodeacademy.exception.password.InvalidPasswordException;
import az.code.lmscodeacademy.exception.users.UserNameExistException;
import az.code.lmscodeacademy.exception.users.UserNotFoundException;
import az.code.lmscodeacademy.repository.authority.AuthorityRepository;
import az.code.lmscodeacademy.repository.user.UserRepository;
import az.code.lmscodeacademy.security.jwt.JwtService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthorityRepository authorityRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    public AuthServiceTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRegisterUserWhenValidSignUpRequest() {
        // Arrange
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail("quliyevv.nicat2003@gmail.com");
        signUpRequest.setUsername("quliyeevn");
        signUpRequest.setPassword("qwerty");

        when(userRepository.existsByEmail(signUpRequest.getEmail())).thenReturn(false);
        when(userRepository.existsByUsername(signUpRequest.getUsername())).thenReturn(false);
        when(authorityRepository.findByAuthority(UserAuthority.ADMIN)).thenReturn(Optional.empty());
        when(authorityRepository.save(any(Authority.class))).thenReturn(new Authority());
        when(passwordEncoder.encode(signUpRequest.getPassword())).thenReturn("$2a$10$xbXQaEb2yKOR/n9YsRxJDewhTvsrEqSGf55WoDg5GT.gQ59zL15vq");
        when(userRepository.save(any(User.class))).thenReturn(new User());
        when(jwtService.issueToken(any(User.class))).thenReturn("token");

        // Act
        ResponseEntity<Response> responseEntity = authService.registerUser(signUpRequest);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(ResponseEntity.ok().build().getStatusCode(), responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getJwt());
    }

    @Test
    public void testRegisterUserWhenUsernameExists() {
        // Arrange
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUsername("existingUsername");

        when(userRepository.existsByUsername(signUpRequest.getUsername())).thenReturn(true);

        // Act and Assert
        UserNameExistException exception = assertThrows(UserNameExistException.class, () -> {
            authService.registerUser(signUpRequest);
        });

        assertEquals(ErrorCodes.USERNAME_ALREADY_EXIST, exception.getErrorCode());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
        verify(jwtService, never()).issueToken(any(User.class));
    }

    @Test
    public void testLoginUserWhenValidLoginRequest() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("quliyevv.nicat2003@gmail.com");
        loginRequest.setPassword("qwerty");

        User user = new User();
        user.setPassword("$2a$10$xbXQaEb2yKOR/n9YsRxJDewhTvsrEqSGf55WoDg5GT.gQ59zL15vq");

        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(true);
        when(jwtService.issueToken(user)).thenReturn("token");

        // Act
        ResponseEntity<Response> responseEntity = authService.loginUser(loginRequest);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(ResponseEntity.ok().build().getStatusCode(), responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getJwt());
    }


    @Test
    public void testLoginUserWhenUserNotFound() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("quliyevv.nicat2003@gmail.com");

        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.empty());

        // Act and Assert
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            authService.loginUser(loginRequest);
        });

        assertEquals(ErrorCodes.USER_NOT_FOUND, exception.getErrorCode());
        verify(passwordEncoder, never()).matches(anyString(), anyString());
        verify(jwtService, never()).issueToken(any(User.class));
    }

    @Test
    void testLoginUser_UserNotFound() {
        // Given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("nonExistingEmail@example.com");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // When / Then
        assertThrows(UserNotFoundException.class, () -> authService.loginUser(loginRequest));
    }

    @Test
    public void testLoginUserInvalidPassword() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("existingEmail@example.com");
        loginRequest.setPassword("invalidPassword");

        User user = new User();
        user.setPassword("$2a$10$xbXQaEb2yKOR/n9YsRxJDewhTvsrEqSGf55WoDg5GT.gQ59zL15vq");

        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(false);

        // Act and Assert
        InvalidPasswordException exception = assertThrows(InvalidPasswordException.class, () -> {
            authService.loginUser(loginRequest);
        });

        assertEquals(ErrorCodes.INVALID_PASSWORD, exception.getErrorCode());
        verify(passwordEncoder, times(1)).matches(eq(loginRequest.getPassword()), anyString());
        verify(jwtService, never()).issueToken(any(User.class));
    }


    // Add more test cases for other scenarios in loginUser method


    @Test
    public void testChangePassword() {
        // Arrange
        Long userId = 1L;
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setOldPassword(oldPassword);
        changePasswordRequest.setNewPassword(newPassword);

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setPassword("encodedOldPassword");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.matches(eq(oldPassword), anyString())).thenReturn(true);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedNewPassword");
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        // Act
        ResponseEntity<UserResponse> responseEntity = authService.changePassword(userId, changePasswordRequest);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        UserResponse userResponse = responseEntity.getBody();
        assertNotNull(userResponse);
        assertEquals(userId, userResponse.getId());
        assertEquals(existingUser.getUsername(), userResponse.getUsername());
        assertEquals(existingUser.getEmail(), userResponse.getEmail());
        assertEquals(existingUser.getAuthorities(), userResponse.getAuthorities());
        assertTrue(userResponse.isPasswordChanged()); // Verify passwordChanged is set to true
        assertNull(userResponse.getGroup()); // Assuming group is not set in response

        verify(userRepository, times(1)).findById(userId);
        verify(passwordEncoder, times(1)).matches(eq(oldPassword), anyString());
        verify(passwordEncoder, times(1)).encode(newPassword);
        verify(userRepository, times(1)).save(existingUser);
    }



    @Test
    public void testChangePassword_UserNotFound() {
        // Arrange
        Long userId = 1L;
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setOldPassword("oldPassword");
        changePasswordRequest.setNewPassword("newPassword");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(UserNotFoundException.class, () -> authService.changePassword(userId, changePasswordRequest));

        verify(userRepository, times(1)).findById(userId);
        verifyNoMoreInteractions(passwordEncoder, userRepository);
    }

    @Test
    public void testChangePassword_InvalidPassword() {
        // Arrange
        Long userId = 1L;
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setOldPassword("wrongOldPassword");
        changePasswordRequest.setNewPassword("newPassword");

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setPassword("encodedOldPassword");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        // Act and Assert
        assertThrows(InvalidPasswordException.class, () -> authService.changePassword(userId, changePasswordRequest));

        verify(userRepository, times(1)).findById(userId);
        verify(passwordEncoder, times(1)).matches("wrongOldPassword", existingUser.getPassword());
        verifyNoMoreInteractions(passwordEncoder, userRepository);
    }

}
