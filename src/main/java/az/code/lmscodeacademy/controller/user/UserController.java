package az.code.lmscodeacademy.controller.user;

import az.code.lmscodeacademy.dto.request.user.UserRequest;
import az.code.lmscodeacademy.dto.response.user.UserResponse;
import az.code.lmscodeacademy.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class UserController {

    private final UserService userService;

    @PostMapping("/groups/{groupName}")
    public ResponseEntity<UserResponse> save(@RequestBody UserRequest userRequest,
                                             @PathVariable String groupName) {
        return new ResponseEntity<>(userService.createUser(userRequest, groupName), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAllUsers() {
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }

}
