package az.code.lmscodeacademy.controller.auth;

import az.code.lmscodeacademy.dto.request.login.LoginRequest;
import az.code.lmscodeacademy.dto.request.signup.SignUpRequest;
import az.code.lmscodeacademy.dto.response.jwt.Response;
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

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Response> registerUser(@RequestBody SignUpRequest signUpRequest) {

        return userService.registerUser(signUpRequest);

    }
    @PostMapping("/signin")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest) {
        return userService.loginUser(loginRequest);
    }

    @GetMapping("/confirmation")
    public ResponseEntity<?> confirmation(@RequestParam("confirmationToken") String confirmationToken) {
        return userService.confirmation(confirmationToken);
    }
}
