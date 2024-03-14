package az.code.lmscodeacademy.controller;

import az.code.lmscodeacademy.dto.request.LoginRequest;
import az.code.lmscodeacademy.dto.request.SignUpRequest;
import az.code.lmscodeacademy.dto.response.Response;
import az.code.lmscodeacademy.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class UserController {

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
