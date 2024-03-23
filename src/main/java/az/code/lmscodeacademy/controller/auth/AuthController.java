package az.code.lmscodeacademy.controller.auth;

import az.code.lmscodeacademy.dto.request.login.LoginRequest;
import az.code.lmscodeacademy.dto.request.password.ChangePasswordRequest;
import az.code.lmscodeacademy.dto.request.signup.SignUpRequest;
import az.code.lmscodeacademy.dto.response.jwt.Response;
import az.code.lmscodeacademy.dto.response.user.UserResponse;
import az.code.lmscodeacademy.service.auth.AuthService;
import az.code.lmscodeacademy.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Response> registerUser(@RequestBody SignUpRequest signUpRequest) {
        return authService.registerUser(signUpRequest);

    }

    @PostMapping("/signin")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest) {
        return authService.loginUser(loginRequest);
    }

    @PostMapping("/changePassword/users/{userId}")
    public ResponseEntity<UserResponse> changePassword(@PathVariable Long userId,
                                                       @RequestBody ChangePasswordRequest request) {
        return authService.changePassword(userId, request);
    }

    @GetMapping("/confirmation")
    public ResponseEntity<?> confirmation(@RequestParam("confirmationToken") String confirmationToken) {
        return authService.confirmation(confirmationToken);
    }
}
